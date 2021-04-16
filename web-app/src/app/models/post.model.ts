<<<<<<< HEAD
import {ImageModel} from './image.model';
import {User} from './user.model';

export class PostModel{
  constructor(public postId:number, public text: string, public image: ImageModel, public date: Date, public user: User) {}
}
=======
import {ImageModel} from './image.model';
import {User} from './user.model';
import {CommentModel} from './comment.model';
import {LikeModel} from './like.model';

export class Post{
  constructor(public id: number, public text: string, public image: ImageModel, public comments: CommentModel[], public date: Date, public user: User, public likes: LikeModel[]) {}
}
>>>>>>> 5100960d8bb1ad14f43945440a5f5dd4c7058ac4
