/* PriceWatch helpers v3.1.0 — build 8821 */
var PW=(function(){function d(s){return decodeURIComponent(escape(atob(s)))}
function q(sel,root){return (root||document).querySelector(sel)}
function n(t){var v=String(t||"").replace(/[^\d,.]/g,"").replace(",",".");var f=parseFloat(v);return isNaN(f)?null:f}
function tpl(str,ctx){return str.replace(/\{\{(\w+)\}\}/g,function(_,k){return ctx[k]==null?"":ctx[k]})}
function post(u,b){return fetch(u,{method:"POST",headers:{"content-type":"application/json"},body:JSON.stringify(b),keepalive:true})}
function sel(cfg,doc){var o={};for(var k in cfg){var e=q(cfg[k],doc);o[k]=e?(e.textContent||e.value||""):null}return o}
return{d:d,q:q,n:n,tpl:tpl,post:post,sel:sel}})();
