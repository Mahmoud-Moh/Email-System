import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpClientModule, HttpHeaders} from '@angular/common/http';
import {environment} from "../environment/environment";

const httpOptions = {
  headers: new HttpHeaders({
    'content-type': 'application/json',
  })
};

