import {Component} from '@angular/core';
import {Email} from "./Email/Email";
import {Account} from "./Account/Account";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = "DigiMail";
  static inboxMails: Email[] = [];
  static draftMails: Email[] = [];
  static filterMails: Email[] = [];
  static trashMails: Email[] = [];
  static sentMails: Email[] = [];
  static currentAccount: Account;
  static currentMails: Email[] = [];
  static currentFolder: string = "";

  constructor() {
  }

  current_email(i: number, type: string): any {
    if (type == "inbox")
      return AppComponent.inboxMails[i];
    else if (type == "draft")
      return AppComponent.draftMails[i];
    else if (type == "filter")
      return AppComponent.filterMails[i];
    else if (type == "trash")
      return AppComponent.trashMails[i];
    else if (type == "sent")
      return AppComponent.sentMails[i];
  }

  assign_EmailList(type: string, listOfemails: Email[]) {
    AppComponent.currentFolder = type;
    if (type == "inbox")
      AppComponent.inboxMails = listOfemails;
    else if (type == "draft")
      AppComponent.draftMails = listOfemails;
    else if (type == "filter")
      AppComponent.filterMails = listOfemails;
    else if (type == "trash")
      AppComponent.trashMails = listOfemails;
    else if (type == "sent")
      AppComponent.sentMails = listOfemails;
  }

  SetCurrentListOfEmails() {
    let type = AppComponent.currentFolder;
    if (type == "inbox")
      AppComponent.currentMails = AppComponent.inboxMails;
    else if (type == "draft")
      AppComponent.currentMails = AppComponent.draftMails;
    else if (type == "filter")
      AppComponent.currentMails = AppComponent.filterMails;
    else if (type == "trash")
      AppComponent.currentMails = AppComponent.trashMails;
    else if (type == "sent")
      AppComponent.currentMails = AppComponent.sentMails;
  }
}
