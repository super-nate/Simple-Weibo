package spittr.entity;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class SpittleForm {

  //the controller did't validate
  @NotNull
  @Size(min=5, max=140, message="can't be empty")
  private String message;
  
  @Min(-180)
  @Max(180)
  private Double longitude;
  
  @Min(-90)
  @Max(90)
  private Double latitude;

  private MultipartFile profilePicture;

  public MultipartFile getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(MultipartFile profilePicture) {
    this.profilePicture = profilePicture;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
  
  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }
}
