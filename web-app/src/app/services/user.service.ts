import {Injectable} from '@angular/core';
import {User} from '../models/user.model';
import {HttpClient} from '@angular/common/http';
import {TokenService} from './Token.service';
import {Post} from '../models/post.model';

@Injectable({providedIn: 'root'})
export class UserService{
  theuser: User = new User(null, 'Abdulla', 'Elsayed', 'Abdulla167', 'male', 'abdullaelsayed167@yahoo.com'
                              , null, '01100661997');

  constructor(private httpClient: HttpClient, private tokenService: TokenService) {
  }

  saveProfileImg(selectedFile: File) {
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', selectedFile, selectedFile.name);
    return this.httpClient.post('http://localhost:8080/user/setProfilePic', uploadImageData, { observe: 'response' });
  }

  getUser() {
    const headers = {
      Authorization: 'Bearer ' + this.tokenService.getToken()
    };
    this.httpClient.get('http://localhost:8080/user',{observe: 'response',headers}).subscribe(( response) => {
      if (response.status === 200 ){
        this.theuser = ( JSON.parse(JSON.stringify(response.body)) as User);
      }
    });
  }

}
