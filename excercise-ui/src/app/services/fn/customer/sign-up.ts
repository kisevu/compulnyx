/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';


export interface SignUp$Params {
  accountType: 'SAVINGS_ACCOUNT' | 'CHECKING_ACCOUNT' | 'CREDIT_ACCOUNT';
}

export function signUp(http: HttpClient, rootUrl: string, params: SignUp$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
  const rb = new RequestBuilder(rootUrl, signUp.PATH, 'post');
  if (params) {
    rb.query('accountType', params.accountType, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<{
      }>;
    })
  );
}

signUp.PATH = '/finance/customer/createAccount';
