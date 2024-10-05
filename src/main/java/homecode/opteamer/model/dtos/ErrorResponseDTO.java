package homecode.opteamer.model.dtos;


public class ErrorResponseDTO extends AbstractResponseDTO{
    public ErrorResponseDTO(String message, boolean success) {
        setMessage(message);
        setSuccess(success);
    }
}
