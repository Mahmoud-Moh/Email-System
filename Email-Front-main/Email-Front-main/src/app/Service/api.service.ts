import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpClientModule, HttpHeaders} from '@angular/common/http';
import {environment} from "../environment/environment";
import {Result} from "../Result";

const httpOptions = {
  headers: new HttpHeaders({
    'content-type': 'application/json',
  })
};

@Injectable()
export class ApiService {
  constructor(private http: HttpClient) {
  }

  signUpPost(body: Object = {}): Observable<Result> {
    return this.http.post<Result>(`${environment.api_url}signup`, JSON.stringify(body), httpOptions);
  }

  SendEmail(user_id: number, body: Object = {}): Observable<Result> {
    return this.http.post<Result>(`${environment.api_url}mail/${user_id}`, JSON.stringify(body), httpOptions);
  }

  ShowEmail(folder: string, sort: string, user_id: number): Observable<Result> {
    return this.http.get<Result>(`${environment.api_url}sort/${folder}/${sort}/${user_id}`, httpOptions);
  }

  SendDraft(user_id: number, body: Object = {}): Observable<any> {
    return this.http.post<any>(`${environment.api_url}draft/${user_id}`, JSON.stringify(body), httpOptions);
  }

}
