package task.prography10th.application.dto;

public record FakerUser(int id, String uuid, String firstname, String lastname, String username, String password,
                        String email, String ip, String macAddress, String website, String image) {
}
