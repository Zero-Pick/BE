package kw.zeropick.common.domain.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String dataSource, long id) {
        super(dataSource + "에서 ID " + id + "를 찾을 수 없습니다.");
    }
}
