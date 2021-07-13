import {Injectable} from "@angular/core";
import {User} from "../models/user.model";
import {HttpClient, HttpErrorResponse, HttpParams} from "@angular/common/http";
import {DeviceModel} from "../models/device.model";
import {TokenService} from "./Token.service";
import {catchError} from "rxjs/operators";
import {UserInfoModel} from "../models/user-info.model";
import {throwError} from "rxjs";

@Injectable({providedIn:"root"})
export class UserService{
  theuser : User = new User("Abdulla", "Elsayed","Abdulla167","male","abdullaelsayed167@yahoo.com"
                              ,null, "01100661997");

  constructor(private httpClient : HttpClient, private tokenService : TokenService) {
  }

  saveProfileImg(selectedFile : File){
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', selectedFile,selectedFile.name);
    return this.httpClient.post('http://localhost:8080/user/setProfilePic', uploadImageData, { observe: 'response' })
  }

  addUserInfo(userInfo: UserInfoModel){
    const headers = {
      'Authorization': 'Basic ' + btoa('mothercare-webapp:6969'),
      'Content-type': 'application/x-www-form-urlencoded'
    };

    return this.httpClient.post('http://localhost:8080/oauth/token', userInfo, {observe: 'response', headers}).pipe(
      catchError(this.handleError)
    );
  }


  private handleError(errResp: HttpErrorResponse) {
    let errorMessage;
    switch (errResp.error.error){
      case ("invalid_grant"):
        errorMessage = "Invalid username or password";
        break;
      case ("user_exist"):
        errorMessage =errResp.error.error_description;
        break;
      case ("server_problem"):
        errorMessage = errResp.error.error_description;
        break;
      default:
        errorMessage =  'Unknown error';
        break;
    }
    return throwError(errorMessage);
  }

}
