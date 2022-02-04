import {User} from "./User";

export class EmailCard{
  sender: User;
  title: string;
  content: string;

  constructor(sender: User, content: string, title: string) {
    this.sender = sender;
    this.content = content;
    this.title = title;
  }
}
