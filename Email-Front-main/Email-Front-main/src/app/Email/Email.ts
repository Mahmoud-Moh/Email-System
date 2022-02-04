import {User} from "./User";

export class Email {
  Sender: string;
  Subject: string;
  Body: string;
  StringDate: string;
  Receiver: string[];
  Priority: number;
  Attachment: string[];
  ID: number;
  checked: boolean = false;

  constructor(Sender: string, Subject: string, Body: string, StringDate: string,
              Receiver: string[], Priority: number, ID: number, Attachment: string[]) {
    this.Sender = Sender;
    this.Subject = Subject;
    this.Body = Body;
    this.StringDate = StringDate;
    this.Receiver = Receiver;
    this.Priority = Priority;
    this.ID = ID;
    this.Attachment = Attachment;
  }

}
