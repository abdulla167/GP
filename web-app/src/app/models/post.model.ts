import {ImageModel} from './image.model';
import {User} from './user.model';

export class Post{
  constructor(public text: string, public image: ImageModel, public date: Date, public user: User) {}

  //
  // get getText(): string {
  //   return this.text;
  // }
  //
  // set setText(value: string) {
  //   this.text = value;
  // }
  //
  // get getImage(): object {
  //   return this.image;
  // }
  //
  // set setImage(value: object) {
  //   this.image = value;
  // }
  //
  // get getDate(): Date {
  //   return this.date;
  // }
  //
  // set setDate(value: Date) {
  //   this.date = value;
  // }
  //
  // get getUser(): User {
  //   return this.user;
  // }
  //
  // set setUser(value: User) {
  //   this.user = value;
  // }
  // hello(){
  //   return 'kio123' + this.text;
  // }
}
