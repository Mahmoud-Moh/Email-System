import { Component, OnInit} from '@angular/core';
import { faBars, faPaperPlane, faFile, faTrash, faCaretDown, faTrashAlt, faEllipsisV} from '@fortawesome/free-solid-svg-icons';
import { AppComponent} from "../../app.component";
import { ActivatedRoute} from "@angular/router";
import {DomSanitizer} from "@angular/platform-browser";
import { homeComponent} from "../home/home.component";
import { Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";


@Component({
  selector: 'app-pages-mail',
  templateUrl: './mail.component.html',
  styleUrls: ['./mail.component.css']
})
export class mailComponent {
  paramQuery = '';
  paramQuery_int : number = 0;
  constructor( private activatedRoute: ActivatedRoute, private sanitizer: DomSanitizer, private router: Router, private http: HttpClient) {
    this.activatedRoute.params.subscribe(data => {
      this.paramQuery = data['id'];
      this.paramQuery_int = +this.paramQuery;
      for (let x of AppComponent.currentMails) {
        if (x.Attachment == null) continue;
        for (let i = 0; i < x.Attachment.length; i++) {
          x.Attachment[i] = <string> this.sanitizer.bypassSecurityTrustUrl(x.Attachment[i]);
          console.log(x.Attachment[i]);
        }
      }
    });
  }
  title = 'untitled';
  faBars = faBars;
  faPaperPlane = faPaperPlane;
  faFile = faFile;
  faTrash = faTrash;
  faCaretDown = faCaretDown;
  faEllipsisV = faEllipsisV;
  image1:string = "assets/menu-of-three-lines.png";
  image2:string = "assets/email_logo2.png";
  appComponent = new AppComponent();
  currentMails = AppComponent.currentMails;

  check_paramQuery(x:number):void{
    if(this.paramQuery_int+1 > this.currentMails.length-1 || this.paramQuery_int-1 < 0){
      //do-nothing
    }
    else {
      this.paramQuery_int += x;
    }
  }

  routeTohome(folder: string): void{
      this.router.navigate([`/home/${folder}`]).then();
  }


}
