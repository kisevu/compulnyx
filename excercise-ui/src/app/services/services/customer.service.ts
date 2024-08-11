/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { accept } from '../fn/customer/accept';
import { Accept$Params } from '../fn/customer/accept';
import { deposit } from '../fn/customer/deposit';
import { Deposit$Params } from '../fn/customer/deposit';
import { getBalance } from '../fn/customer/get-balance';
import { GetBalance$Params } from '../fn/customer/get-balance';
import { miniStatement } from '../fn/customer/mini-statement';
import { MiniStatement$Params } from '../fn/customer/mini-statement';
import { signUp } from '../fn/customer/sign-up';
import { SignUp$Params } from '../fn/customer/sign-up';

@Injectable({ providedIn: 'root' })
export class CustomerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `accept()` */
  static readonly AcceptPath = '/finance/customer/withdraw';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `accept()` instead.
   *
   * This method doesn't expect any request body.
   */
  accept$Response(params?: Accept$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return accept(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `accept$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  accept(params?: Accept$Params, context?: HttpContext): Observable<{
}> {
    return this.accept$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `deposit()` */
  static readonly DepositPath = '/finance/customer/deposit';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deposit()` instead.
   *
   * This method doesn't expect any request body.
   */
  deposit$Response(params?: Deposit$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return deposit(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deposit$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deposit(params?: Deposit$Params, context?: HttpContext): Observable<{
}> {
    return this.deposit$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `signUp()` */
  static readonly SignUpPath = '/finance/customer/createAccount';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `signUp()` instead.
   *
   * This method doesn't expect any request body.
   */
  signUp$Response(params: SignUp$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return signUp(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `signUp$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  signUp(params: SignUp$Params, context?: HttpContext): Observable<{
}> {
    return this.signUp$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `miniStatement()` */
  static readonly MiniStatementPath = '/finance/customer/ministatement';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `miniStatement()` instead.
   *
   * This method doesn't expect any request body.
   */
  miniStatement$Response(params?: MiniStatement$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return miniStatement(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `miniStatement$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  miniStatement(params?: MiniStatement$Params, context?: HttpContext): Observable<{
}> {
    return this.miniStatement$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `getBalance()` */
  static readonly GetBalancePath = '/finance/customer/balance';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getBalance()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBalance$Response(params?: GetBalance$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return getBalance(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getBalance$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBalance(params?: GetBalance$Params, context?: HttpContext): Observable<{
}> {
    return this.getBalance$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

}
