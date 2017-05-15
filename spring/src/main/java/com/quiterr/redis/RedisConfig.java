package com.quiterr.redis;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuetao on 2017/5/2.
 */
@Configuration
@EnableRedisRepositories
public class RedisConfig {
    /**
     * Redis connection configuration.
     */
    @Configuration
    protected static class RedisConnectionConfiguration {

        private final RedisProperties properties;

        private final RedisSentinelConfiguration sentinelConfiguration;

        private final RedisClusterConfiguration clusterConfiguration;

        /**
         * Instantiates a new Redis connection configuration.
         *
         * @param properties                    the properties
         * @param sentinelConfigurationProvider the sentinel configuration provider
         * @param clusterConfigurationProvider  the cluster configuration provider
         */
        public RedisConnectionConfiguration(RedisProperties properties,
                                            ObjectProvider<RedisSentinelConfiguration> sentinelConfigurationProvider,
                                            ObjectProvider<RedisClusterConfiguration> clusterConfigurationProvider) {
            this.properties = properties;
            this.sentinelConfiguration = sentinelConfigurationProvider.getIfAvailable();
            this.clusterConfiguration = clusterConfigurationProvider.getIfAvailable();
        }

        /**
         * Redis connection factory jedis connection factory.
         *
         * @return the jedis connection factory
         * @throws UnknownHostException the unknown host exception
         */
        @Bean
        public JedisConnectionFactory redisConnectionFactory()
                throws UnknownHostException {
            return applyProperties(createJedisConnectionFactory());
        }

        /**
         * Apply properties jedis connection factory.
         *
         * @param factory the factory
         * @return the jedis connection factory
         */
        protected final JedisConnectionFactory applyProperties(JedisConnectionFactory factory) {
            factory.setHostName(this.properties.getHost());
            factory.setPort(this.properties.getPort());
            if (this.properties.getPassword() != null) {
                factory.setPassword(this.properties.getPassword());
            }
            factory.setDatabase(this.properties.getDatabase());
            if (this.properties.getTimeout() > 0) {
                factory.setTimeout(this.properties.getTimeout());
            }
            return factory;
        }

        /**
         * Gets sentinel config.
         *
         * @return the sentinel config
         */
        protected final RedisSentinelConfiguration getSentinelConfig() {
            if (this.sentinelConfiguration != null) {
                return this.sentinelConfiguration;
            }
            Sentinel sentinelProperties = this.properties.getSentinel();
            if (sentinelProperties != null && sentinelProperties.getNodes() != null && sentinelProperties.getMaster() != null) {
                RedisSentinelConfiguration config = new RedisSentinelConfiguration();
                config.master(sentinelProperties.getMaster());
                config.setSentinels(createSentinels(sentinelProperties));
                return config;
            }
            return null;
        }


        /**
         * Gets cluster configuration.
         *
         * @return the cluster configuration
         */
        protected final RedisClusterConfiguration getClusterConfiguration() {
            if (this.clusterConfiguration != null) {
                return this.clusterConfiguration;
            }
            if (this.properties.getCluster() == null || this.properties.getCluster().getNodes() == null) {
                return null;
            }
            Cluster clusterProperties = this.properties.getCluster();
            RedisClusterConfiguration config = new RedisClusterConfiguration(
                    clusterProperties.getNodes());

            if (clusterProperties.getMaxRedirects() != null) {
                config.setMaxRedirects(clusterProperties.getMaxRedirects());
            }
            return config;
        }

        private List<RedisNode> createSentinels(Sentinel sentinel) {
            List<RedisNode> nodes = new ArrayList<RedisNode>();
            for (String node : StringUtils
                    .commaDelimitedListToStringArray(sentinel.getNodes())) {
                try {
                    String[] parts = StringUtils.split(node, ":");
                    Assert.state(parts.length == 2, "Must be defined as 'host:port'");
                    nodes.add(new RedisNode(parts[0], Integer.valueOf(parts[1])));
                } catch (RuntimeException ex) {
                    throw new IllegalStateException(
                            "Invalid redis sentinel " + "property '" + node + "'", ex);
                }
            }
            return nodes;
        }

        private JedisConnectionFactory createJedisConnectionFactory() {
            JedisPoolConfig poolConfig = this.properties.getPool() != null
                    ? jedisPoolConfig() : new JedisPoolConfig();

            if (getSentinelConfig() != null) {
                return new JedisConnectionFactory(getSentinelConfig(), poolConfig);
            }
            if (getClusterConfiguration() != null) {
                return new JedisConnectionFactory(getClusterConfiguration(), poolConfig);
            }
            return new JedisConnectionFactory(poolConfig);
        }

        private JedisPoolConfig jedisPoolConfig() {
            JedisPoolConfig config = new JedisPoolConfig();
            Pool props = this.properties.getPool();
            config.setMaxTotal(props.getMaxActive());
            config.setMaxIdle(props.getMaxIdle());
            config.setMinIdle(props.getMinIdle());
            config.setMaxWaitMillis(props.getMaxWait());
            return config;
        }

    }

    /**
     * Redis template redis template.
     *
     * @param redisConnectionFactory the redis connection factory
     * @return the redis template
     * @throws UnknownHostException the unknown host exception
     */
    @Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new GenericToStringSerializer<Object>(Object.class));
        template.setHashValueSerializer(fastJson2JsonRedisSerializer());
        template.setValueSerializer(fastJson2JsonRedisSerializer());
        return template;
    }


    /**
     * Fast json 2 json redis serializer redis serializer.
     *
     * @return the redis serializer
     */
    @Bean
    @SuppressWarnings("rawtypes")
    public RedisSerializer fastJson2JsonRedisSerializer() {
        return new FastJson2JsonRedisSerializer<Object>(Object.class);
    }

}