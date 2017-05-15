package com.quiterr.spring;

/**
 * Created by xuetao on 2017/5/2.
 */
public class StormException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -2630492983400325714L;

    public StormException() {
        super();
    }

    public StormException(String msg) {
        super(msg);
    }

    public StormException(Throwable cause) {
        super(cause);
    }

    public StormException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * <p>
     * Return the exception that is the underlying cause of this exception.
     * </p>
     * <p>
     * <p>
     * This may be used to find more detail about the cause of the error.
     * </p>
     *
     * @return the underlying exception, or <code>null</code> if there is not one.
     */
    public Throwable getUnderlyingException() {
        return super.getCause();
    }

    @Override
    public String toString() {
        Throwable cause = this.getUnderlyingException();
        if ((cause == null) || (cause == this)) {
            return super.toString();
        } else {
            return super.toString() + " [See nested exception: " + cause + "]";
        }
    }
}

