import {Injectable} from "@angular/core";
import {User} from "../models/user.model";
import {HttpClient} from "@angular/common/http";

@Injectable({providedIn:"root"})
export class UserService{
  theuser : User = new User("Abdulla", "Elsayed","Abdulla167","male","abdullaelsayed167@yahoo.com"
                              ,null, "01100661997");

  constructor(private httpClient : HttpClient) {
  }

  saveProfileImg(selectedFile : File){
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', selectedFile,selectedFile.name);
    return this.httpClient.post('http://localhost:8080/user/setProfilePic', uploadImageData, { observe: 'response' })
  }



}
