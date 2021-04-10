import {ImageModel} from './image.model';
import {User} from './user.model';
import {CommentModel} from './comment.model';
import {LikeModel} from './like.model';

export class Post{
  constructor(public id: number, public text: string, public image: ImageModel, public comments: CommentModel[], public date: Date, public user: User, public likes: LikeModel[]) {}
}
