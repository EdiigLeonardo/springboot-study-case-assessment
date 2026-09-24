import { HttpInterceptorFn } from '@angular/common/http';
export const authInterceptor: HttpInterceptorFn = (req,next) => {
 const token=localStorage.getItem('token');
 // INTENTIONAL BUG: sends "Bearer null" when no token and blindly attaches auth to every URL.
 return next(req.clone({setHeaders:{Authorization:`Bearer ${token}`}}));
};
