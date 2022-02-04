import {Component} from '@angular/core';

import {
  faBars,
  faPaperPlane,
  faFile,
  faTrash,
  faCaretDown,
  faEllipsisV,
  faRedoAlt,
  faFilter
} from '@fortawesome/free-solid-svg-icons';
import {AppComponent} from "../../app.component";
import {ApiService} from "../../Service/api.service";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {Result} from "../../Result";
import {Email} from "../../Email/Email";
import {Account} from "../../Account/Account";
import {DomSanitizer} from "@angular/platform-browser";
import {ActivatedRoute} from "@angular/router";
import { NgxPaginationModule} from "ngx-pagination";


@Component({
  selector: 'app-pages-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class homeComponent {
  USERNAME!: string;
  chosenFolder!: string;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private http: HttpClient, private sanitizer: DomSanitizer) {
    this.USERNAME = AppComponent.currentAccount.EmailAddress;
    this.activatedRoute.params.subscribe(data => {
      this.showEmails(data['folder'], 'date', AppComponent.currentAccount.ID);
    })
  }

  appComponent = new AppComponent();
  title = 'digiMail';
  faBars = faBars;
  faPaperPlane = faPaperPlane;
  faFile = faFile;
  faTrash = faTrash;
  faCaretDown = faCaretDown;
  faEllipsisV = faEllipsisV;
  faArrowAltCircleUp = faRedoAlt;
  faFilter = faFilter;
  image1: string = "assets/menu-of-three-lines.png";
  image2: string = "assets/email_logo2.png";
  x2: string | ArrayBuffer | null = "";


  currentMails = AppComponent.currentMails;
  count = 0;
  compose_state = false;

  //Compose Variables
  To!: string;
  Title!: string;
  Content!: string;
  selectedPriority !: string;
  attachment: any[] = [];

  //Search Variables
  search_input: string = "";
  search_type: string = "";
  checkedEmails!: Email[];

  //Filter Variables
  FilterSubject !: string;
  FilterSender !: string;

  //Sort
  SortCriteria!: string;

  checked() {
    console.log("CHECKED");
    this.checkedEmails = AppComponent.currentMails.reduce((acc: Email[], email) => {
      if (email.checked) {
        acc.push(email);

      }
      return acc;
    }, [])
  }

  delete() {
    this.checked();

    for (let checkedEmail of this.checkedEmails) {
      this.http.delete(`http://localhost:8080/email/delete/${AppComponent.currentAccount.ID}/${AppComponent.currentFolder}/${checkedEmail.ID}`).subscribe()
      AppComponent.currentMails.splice(AppComponent.currentMails.indexOf(checkedEmail), 1);
    }
    AppComponent.trashMails = this.checkedEmails;
    this.currentMails = AppComponent.currentMails;
  }

  markChecked(index: number) {
    AppComponent.currentMails[index].checked = true;
  }

  sendComposedEmail(): void {
    new ApiService(this.http).SendEmail(AppComponent.currentAccount.ID, {
        "Sender": AppComponent.currentAccount.EmailAddress,
        "Receiver": this.To.split("-"),
        "Subject": this.Title,
        "Body": this.Content,
        "Priority": +this.selectedPriority,
        "Attachment": this.attachment
      }
    ).subscribe(response => {
      console.log("Compose and send, performed correctly.");
    })

    AppComponent.sentMails.push(new Email(AppComponent.currentAccount.EmailAddress, this.Title,
      this.Content, "Dummydate", this.To.split("-"), +(this.selectedPriority), AppComponent.currentAccount.ID, this.attachment));
  }

  check(x: string): string {
    if (x.length > 130) {
      return x.slice(0, 130) + ".....";
    } else {
      return x;
    }
  }

  showEmails(folder: string, sort: string, user_id: number): void {
    new ApiService(this.http).ShowEmail(folder, sort, user_id).subscribe(
      response => {
        this.appComponent.assign_EmailList(folder, response.emails);
        this.appComponent.SetCurrentListOfEmails();
        this.currentMails = AppComponent.currentMails;
      }
    )
  }

  search_handling(): void {
    if (this.search_input == "" || this.search_type == "")
      return;
    else {
      this.http.get<Result>(`http://localhost:8080/email/search/${AppComponent.currentFolder}/${this.search_type}/${AppComponent.currentAccount.ID}`,
        {
          params: {value: this.search_input},
          observe: "response"
        }).subscribe(response => {
        //Assign the searched emails to the current folder, assign the current folder to currentEmails
        this.appComponent.assign_EmailList('filter', response.body!.emails);
        this.appComponent.SetCurrentListOfEmails();
        this.currentMails = AppComponent.currentMails;
      })
    }
  }

  emailsPerpageCount: number = 11;
  p: number = 1;
  filter_handling(): void {
    if (this.FilterSender == null && this.FilterSubject == null) {/*do nothing*/
    } else if (this.FilterSender != null && this.FilterSubject == null) {
      this.sendFilterRequest('sender', this.FilterSender);
    } else if (this.FilterSender == null && this.FilterSubject != null) {
      this.sendFilterRequest('subject', this.FilterSubject);
    } else if (this.FilterSender != null && this.FilterSubject != null) {
      this.sendFilterRequest('sender&subject', this.FilterSender);
    }
  }

  sort_handling(): void {
    this.showEmails(AppComponent.currentFolder, this.SortCriteria, AppComponent.currentAccount.ID);
  }

  sendFilterRequest(filter: string, value: string) {
    console.log("value:", value, "filter: ", filter);
    this.http.get<Result>(`http://localhost:8080/email/filter/${AppComponent.currentFolder}/${filter}/${AppComponent.currentAccount.ID}`,
      {
        params: {value: value},
        observe: "response"
      }).subscribe(response => {
      //Assign the searched emails to the current folder, assign the current folder to currentEmails
      this.appComponent.assign_EmailList('filter', response.body!.emails);
      this.appComponent.SetCurrentListOfEmails();
      this.currentMails = AppComponent.currentMails;
    })
  }


  inboxLoad() {
    AppComponent.currentMails = AppComponent.inboxMails;
    AppComponent.currentFolder = 'inbox';
    this.currentMails = AppComponent.currentMails;
  }

  sentLoad() {
    new ApiService(this.http).ShowEmail('sent', 'date', AppComponent.currentAccount.ID).subscribe(
      response => {
        this.appComponent.assign_EmailList('sent', response.emails);
        this.appComponent.SetCurrentListOfEmails();
        this.currentMails = AppComponent.currentMails;
      }
    )
    AppComponent.currentFolder = 'sent';
  }

  draftSend(draftEmail: Email) {
    console.log("draftSend");
    console.log(draftEmail.Body);
    console.log(AppComponent.currentAccount.ID);
    new ApiService(this.http).SendDraft(AppComponent.currentAccount.ID, {
      "Sender": AppComponent.currentAccount.EmailAddress,
      "Receiver": draftEmail.Receiver,
      "Subject": draftEmail.Subject,
      "Body": draftEmail.Body,
      "Priority": +draftEmail.Priority,
      "Attachment": draftEmail.Attachment
    }).subscribe(response=>{});
  }

  draftLoad() {
    console.log("Mytteeeween");
    new ApiService(this.http).ShowEmail('draft', 'date', AppComponent.currentAccount.ID).subscribe(
      response => {
        console.log("hiiiiiiiiiiii");
        for(let i=0; i<response.emails.length; i++){
          console.log(response.emails[i].Body);
        }
        this.appComponent.assign_EmailList('draft', response.emails);
        AppComponent.currentFolder = 'draft';
        this.appComponent.SetCurrentListOfEmails();
        this.currentMails = AppComponent.currentMails;
        console.log(this.currentMails[0]);
      }
    )
  }

  trashLoad() {
    new ApiService(this.http).ShowEmail('trash', 'date', AppComponent.currentAccount.ID).subscribe(
      response => {
        AppComponent.currentFolder = 'trash';
        this.appComponent.assign_EmailList('trash', response.emails);
        this.appComponent.SetCurrentListOfEmails();
        this.currentMails = AppComponent.trashMails;
      }
    )
  }


  toggle_compose(): void {
    if (this.compose_state && (this.To != null && this.Title != null && this.selectedPriority != null
      && this.Content != null)) {
      console.log("Fuck");
      let draftEmail = new Email(AppComponent.currentAccount.EmailAddress, this.Title,
        this.Content, "DummyDate", this.To.split('-'), +this.selectedPriority, AppComponent.currentAccount.ID, this.attachment);
      AppComponent.draftMails.push(draftEmail);
      this.draftSend(draftEmail);
      console.log("dRAFT: ", draftEmail.Body);
      for(let i=0; i<AppComponent.draftMails.length; i++){
        console.log("->", AppComponent.draftMails[i]);
      }
    }
    this.compose_state = !this.compose_state;
  }

  async Refresh() {
    let flag = false;
    await this.http.get<Result>('http://localhost:8080/email/login', {
      params: {
        email: AppComponent.currentAccount.EmailAddress,
        password: AppComponent.currentAccount.Pass
      },
      observe: 'response'
    }).subscribe(response => {
      if (response != null) {
        AppComponent.currentAccount = new Account("dummy", AppComponent.currentAccount.EmailAddress,
          AppComponent.currentAccount.Pass, response.body!.result);
        flag = true;
      }
    })
    if (AppComponent.currentFolder == 'inbox') {
      this.showEmails('inbox', 'date', AppComponent.currentAccount.ID);
    } else if (AppComponent.currentFolder == 'sent') {
      this.sentLoad();
    }
    flag = false;

  }

  url: any[] = [];

  selectFile(event: any) { //Angular 11, for stricter type
    let path;
    if (!event.target.files[0] || event.target.files[0].length == 0) {
      return;
    }
    path = event.target.files[0];
    //saveAs(path);
    path = URL.createObjectURL(path);
    this.attachment.push(path);
    path = <string>this.sanitizer.bypassSecurityTrustUrl(path);
    this.url.push(path);
  }
}
