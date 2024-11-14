package kw.zeropick.member.domain.exception;

public class ConfirmPasswordMismatchException extends RuntimeException {
    public ConfirmPasswordMismatchException() {
        super("Passwords do not match");
    }
}
