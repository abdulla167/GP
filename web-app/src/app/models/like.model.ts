import {ImageModel} from './image.model';
import {User} from './user.model';
import {Post} from './post.model';

export class LikeModel {
  constructor(public id: number, public post: Post , public user: User){
  }
}
