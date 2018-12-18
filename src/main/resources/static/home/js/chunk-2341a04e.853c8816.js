(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2341a04e"],{"897d":function(module,exports,__webpack_require__){(function(process,global){var __WEBPACK_AMD_DEFINE_RESULT__;
/**
 * [js-md5]{@link https://github.com/emn178/js-md5}
 *
 * @namespace md5
 * @version 0.7.3
 * @author Chen, Yi-Cyuan [emn178@gmail.com]
 * @copyright Chen, Yi-Cyuan 2014-2017
 * @license MIT
 */
/**
 * [js-md5]{@link https://github.com/emn178/js-md5}
 *
 * @namespace md5
 * @version 0.7.3
 * @author Chen, Yi-Cyuan [emn178@gmail.com]
 * @copyright Chen, Yi-Cyuan 2014-2017
 * @license MIT
 */
(function(){"use strict";var ERROR="input is invalid type",WINDOW="object"===typeof window,root=WINDOW?window:{};root.JS_MD5_NO_WINDOW&&(WINDOW=!1);var WEB_WORKER=!WINDOW&&"object"===typeof self,NODE_JS=!root.JS_MD5_NO_NODE_JS&&"object"===typeof process&&process.versions&&process.versions.node;NODE_JS?root=global:WEB_WORKER&&(root=self);var COMMON_JS=!root.JS_MD5_NO_COMMON_JS&&"object"===typeof module&&module.exports,AMD=__webpack_require__("b342"),ARRAY_BUFFER=!root.JS_MD5_NO_ARRAY_BUFFER&&"undefined"!==typeof ArrayBuffer,HEX_CHARS="0123456789abcdef".split(""),EXTRA=[128,32768,8388608,-2147483648],SHIFT=[0,8,16,24],OUTPUT_TYPES=["hex","array","digest","buffer","arrayBuffer","base64"],BASE64_ENCODE_CHAR="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".split(""),blocks=[],buffer8;if(ARRAY_BUFFER){var buffer=new ArrayBuffer(68);buffer8=new Uint8Array(buffer),blocks=new Uint32Array(buffer)}!root.JS_MD5_NO_NODE_JS&&Array.isArray||(Array.isArray=function(e){return"[object Array]"===Object.prototype.toString.call(e)}),!ARRAY_BUFFER||!root.JS_MD5_NO_ARRAY_BUFFER_IS_VIEW&&ArrayBuffer.isView||(ArrayBuffer.isView=function(e){return"object"===typeof e&&e.buffer&&e.buffer.constructor===ArrayBuffer});var createOutputMethod=function(e){return function(r){return new Md5(!0).update(r)[e]()}},createMethod=function(){var e=createOutputMethod("hex");NODE_JS&&(e=nodeWrap(e)),e.create=function(){return new Md5},e.update=function(r){return e.create().update(r)};for(var r=0;r<OUTPUT_TYPES.length;++r){var t=OUTPUT_TYPES[r];e[t]=createOutputMethod(t)}return e},nodeWrap=function(method){var crypto=eval("require('crypto')"),Buffer=eval("require('buffer').Buffer"),nodeMethod=function(e){if("string"===typeof e)return crypto.createHash("md5").update(e,"utf8").digest("hex");if(null===e||void 0===e)throw ERROR;return e.constructor===ArrayBuffer&&(e=new Uint8Array(e)),Array.isArray(e)||ArrayBuffer.isView(e)||e.constructor===Buffer?crypto.createHash("md5").update(new Buffer(e)).digest("hex"):method(e)};return nodeMethod};function Md5(e){if(e)blocks[0]=blocks[16]=blocks[1]=blocks[2]=blocks[3]=blocks[4]=blocks[5]=blocks[6]=blocks[7]=blocks[8]=blocks[9]=blocks[10]=blocks[11]=blocks[12]=blocks[13]=blocks[14]=blocks[15]=0,this.blocks=blocks,this.buffer8=buffer8;else if(ARRAY_BUFFER){var r=new ArrayBuffer(68);this.buffer8=new Uint8Array(r),this.blocks=new Uint32Array(r)}else this.blocks=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];this.h0=this.h1=this.h2=this.h3=this.start=this.bytes=this.hBytes=0,this.finalized=this.hashed=!1,this.first=!0}Md5.prototype.update=function(e){if(!this.finalized){var r,t=typeof e;if("string"!==t){if("object"!==t)throw ERROR;if(null===e)throw ERROR;if(ARRAY_BUFFER&&e.constructor===ArrayBuffer)e=new Uint8Array(e);else if(!Array.isArray(e)&&(!ARRAY_BUFFER||!ArrayBuffer.isView(e)))throw ERROR;r=!0}var s,a,i=0,o=e.length,n=this.blocks,l=this.buffer8;while(i<o){if(this.hashed&&(this.hashed=!1,n[0]=n[16],n[16]=n[1]=n[2]=n[3]=n[4]=n[5]=n[6]=n[7]=n[8]=n[9]=n[10]=n[11]=n[12]=n[13]=n[14]=n[15]=0),r)if(ARRAY_BUFFER)for(a=this.start;i<o&&a<64;++i)l[a++]=e[i];else for(a=this.start;i<o&&a<64;++i)n[a>>2]|=e[i]<<SHIFT[3&a++];else if(ARRAY_BUFFER)for(a=this.start;i<o&&a<64;++i)s=e.charCodeAt(i),s<128?l[a++]=s:s<2048?(l[a++]=192|s>>6,l[a++]=128|63&s):s<55296||s>=57344?(l[a++]=224|s>>12,l[a++]=128|s>>6&63,l[a++]=128|63&s):(s=65536+((1023&s)<<10|1023&e.charCodeAt(++i)),l[a++]=240|s>>18,l[a++]=128|s>>12&63,l[a++]=128|s>>6&63,l[a++]=128|63&s);else for(a=this.start;i<o&&a<64;++i)s=e.charCodeAt(i),s<128?n[a>>2]|=s<<SHIFT[3&a++]:s<2048?(n[a>>2]|=(192|s>>6)<<SHIFT[3&a++],n[a>>2]|=(128|63&s)<<SHIFT[3&a++]):s<55296||s>=57344?(n[a>>2]|=(224|s>>12)<<SHIFT[3&a++],n[a>>2]|=(128|s>>6&63)<<SHIFT[3&a++],n[a>>2]|=(128|63&s)<<SHIFT[3&a++]):(s=65536+((1023&s)<<10|1023&e.charCodeAt(++i)),n[a>>2]|=(240|s>>18)<<SHIFT[3&a++],n[a>>2]|=(128|s>>12&63)<<SHIFT[3&a++],n[a>>2]|=(128|s>>6&63)<<SHIFT[3&a++],n[a>>2]|=(128|63&s)<<SHIFT[3&a++]);this.lastByteIndex=a,this.bytes+=a-this.start,a>=64?(this.start=a-64,this.hash(),this.hashed=!0):this.start=a}return this.bytes>4294967295&&(this.hBytes+=this.bytes/4294967296<<0,this.bytes=this.bytes%4294967296),this}},Md5.prototype.finalize=function(){if(!this.finalized){this.finalized=!0;var e=this.blocks,r=this.lastByteIndex;e[r>>2]|=EXTRA[3&r],r>=56&&(this.hashed||this.hash(),e[0]=e[16],e[16]=e[1]=e[2]=e[3]=e[4]=e[5]=e[6]=e[7]=e[8]=e[9]=e[10]=e[11]=e[12]=e[13]=e[14]=e[15]=0),e[14]=this.bytes<<3,e[15]=this.hBytes<<3|this.bytes>>>29,this.hash()}},Md5.prototype.hash=function(){var e,r,t,s,a,i,o=this.blocks;this.first?(e=o[0]-680876937,e=(e<<7|e>>>25)-271733879<<0,s=(-1732584194^2004318071&e)+o[1]-117830708,s=(s<<12|s>>>20)+e<<0,t=(-271733879^s&(-271733879^e))+o[2]-1126478375,t=(t<<17|t>>>15)+s<<0,r=(e^t&(s^e))+o[3]-1316259209,r=(r<<22|r>>>10)+t<<0):(e=this.h0,r=this.h1,t=this.h2,s=this.h3,e+=(s^r&(t^s))+o[0]-680876936,e=(e<<7|e>>>25)+r<<0,s+=(t^e&(r^t))+o[1]-389564586,s=(s<<12|s>>>20)+e<<0,t+=(r^s&(e^r))+o[2]+606105819,t=(t<<17|t>>>15)+s<<0,r+=(e^t&(s^e))+o[3]-1044525330,r=(r<<22|r>>>10)+t<<0),e+=(s^r&(t^s))+o[4]-176418897,e=(e<<7|e>>>25)+r<<0,s+=(t^e&(r^t))+o[5]+1200080426,s=(s<<12|s>>>20)+e<<0,t+=(r^s&(e^r))+o[6]-1473231341,t=(t<<17|t>>>15)+s<<0,r+=(e^t&(s^e))+o[7]-45705983,r=(r<<22|r>>>10)+t<<0,e+=(s^r&(t^s))+o[8]+1770035416,e=(e<<7|e>>>25)+r<<0,s+=(t^e&(r^t))+o[9]-1958414417,s=(s<<12|s>>>20)+e<<0,t+=(r^s&(e^r))+o[10]-42063,t=(t<<17|t>>>15)+s<<0,r+=(e^t&(s^e))+o[11]-1990404162,r=(r<<22|r>>>10)+t<<0,e+=(s^r&(t^s))+o[12]+1804603682,e=(e<<7|e>>>25)+r<<0,s+=(t^e&(r^t))+o[13]-40341101,s=(s<<12|s>>>20)+e<<0,t+=(r^s&(e^r))+o[14]-1502002290,t=(t<<17|t>>>15)+s<<0,r+=(e^t&(s^e))+o[15]+1236535329,r=(r<<22|r>>>10)+t<<0,e+=(t^s&(r^t))+o[1]-165796510,e=(e<<5|e>>>27)+r<<0,s+=(r^t&(e^r))+o[6]-1069501632,s=(s<<9|s>>>23)+e<<0,t+=(e^r&(s^e))+o[11]+643717713,t=(t<<14|t>>>18)+s<<0,r+=(s^e&(t^s))+o[0]-373897302,r=(r<<20|r>>>12)+t<<0,e+=(t^s&(r^t))+o[5]-701558691,e=(e<<5|e>>>27)+r<<0,s+=(r^t&(e^r))+o[10]+38016083,s=(s<<9|s>>>23)+e<<0,t+=(e^r&(s^e))+o[15]-660478335,t=(t<<14|t>>>18)+s<<0,r+=(s^e&(t^s))+o[4]-405537848,r=(r<<20|r>>>12)+t<<0,e+=(t^s&(r^t))+o[9]+568446438,e=(e<<5|e>>>27)+r<<0,s+=(r^t&(e^r))+o[14]-1019803690,s=(s<<9|s>>>23)+e<<0,t+=(e^r&(s^e))+o[3]-187363961,t=(t<<14|t>>>18)+s<<0,r+=(s^e&(t^s))+o[8]+1163531501,r=(r<<20|r>>>12)+t<<0,e+=(t^s&(r^t))+o[13]-1444681467,e=(e<<5|e>>>27)+r<<0,s+=(r^t&(e^r))+o[2]-51403784,s=(s<<9|s>>>23)+e<<0,t+=(e^r&(s^e))+o[7]+1735328473,t=(t<<14|t>>>18)+s<<0,r+=(s^e&(t^s))+o[12]-1926607734,r=(r<<20|r>>>12)+t<<0,a=r^t,e+=(a^s)+o[5]-378558,e=(e<<4|e>>>28)+r<<0,s+=(a^e)+o[8]-2022574463,s=(s<<11|s>>>21)+e<<0,i=s^e,t+=(i^r)+o[11]+1839030562,t=(t<<16|t>>>16)+s<<0,r+=(i^t)+o[14]-35309556,r=(r<<23|r>>>9)+t<<0,a=r^t,e+=(a^s)+o[1]-1530992060,e=(e<<4|e>>>28)+r<<0,s+=(a^e)+o[4]+1272893353,s=(s<<11|s>>>21)+e<<0,i=s^e,t+=(i^r)+o[7]-155497632,t=(t<<16|t>>>16)+s<<0,r+=(i^t)+o[10]-1094730640,r=(r<<23|r>>>9)+t<<0,a=r^t,e+=(a^s)+o[13]+681279174,e=(e<<4|e>>>28)+r<<0,s+=(a^e)+o[0]-358537222,s=(s<<11|s>>>21)+e<<0,i=s^e,t+=(i^r)+o[3]-722521979,t=(t<<16|t>>>16)+s<<0,r+=(i^t)+o[6]+76029189,r=(r<<23|r>>>9)+t<<0,a=r^t,e+=(a^s)+o[9]-640364487,e=(e<<4|e>>>28)+r<<0,s+=(a^e)+o[12]-421815835,s=(s<<11|s>>>21)+e<<0,i=s^e,t+=(i^r)+o[15]+530742520,t=(t<<16|t>>>16)+s<<0,r+=(i^t)+o[2]-995338651,r=(r<<23|r>>>9)+t<<0,e+=(t^(r|~s))+o[0]-198630844,e=(e<<6|e>>>26)+r<<0,s+=(r^(e|~t))+o[7]+1126891415,s=(s<<10|s>>>22)+e<<0,t+=(e^(s|~r))+o[14]-1416354905,t=(t<<15|t>>>17)+s<<0,r+=(s^(t|~e))+o[5]-57434055,r=(r<<21|r>>>11)+t<<0,e+=(t^(r|~s))+o[12]+1700485571,e=(e<<6|e>>>26)+r<<0,s+=(r^(e|~t))+o[3]-1894986606,s=(s<<10|s>>>22)+e<<0,t+=(e^(s|~r))+o[10]-1051523,t=(t<<15|t>>>17)+s<<0,r+=(s^(t|~e))+o[1]-2054922799,r=(r<<21|r>>>11)+t<<0,e+=(t^(r|~s))+o[8]+1873313359,e=(e<<6|e>>>26)+r<<0,s+=(r^(e|~t))+o[15]-30611744,s=(s<<10|s>>>22)+e<<0,t+=(e^(s|~r))+o[6]-1560198380,t=(t<<15|t>>>17)+s<<0,r+=(s^(t|~e))+o[13]+1309151649,r=(r<<21|r>>>11)+t<<0,e+=(t^(r|~s))+o[4]-145523070,e=(e<<6|e>>>26)+r<<0,s+=(r^(e|~t))+o[11]-1120210379,s=(s<<10|s>>>22)+e<<0,t+=(e^(s|~r))+o[2]+718787259,t=(t<<15|t>>>17)+s<<0,r+=(s^(t|~e))+o[9]-343485551,r=(r<<21|r>>>11)+t<<0,this.first?(this.h0=e+1732584193<<0,this.h1=r-271733879<<0,this.h2=t-1732584194<<0,this.h3=s+271733878<<0,this.first=!1):(this.h0=this.h0+e<<0,this.h1=this.h1+r<<0,this.h2=this.h2+t<<0,this.h3=this.h3+s<<0)},Md5.prototype.hex=function(){this.finalize();var e=this.h0,r=this.h1,t=this.h2,s=this.h3;return HEX_CHARS[e>>4&15]+HEX_CHARS[15&e]+HEX_CHARS[e>>12&15]+HEX_CHARS[e>>8&15]+HEX_CHARS[e>>20&15]+HEX_CHARS[e>>16&15]+HEX_CHARS[e>>28&15]+HEX_CHARS[e>>24&15]+HEX_CHARS[r>>4&15]+HEX_CHARS[15&r]+HEX_CHARS[r>>12&15]+HEX_CHARS[r>>8&15]+HEX_CHARS[r>>20&15]+HEX_CHARS[r>>16&15]+HEX_CHARS[r>>28&15]+HEX_CHARS[r>>24&15]+HEX_CHARS[t>>4&15]+HEX_CHARS[15&t]+HEX_CHARS[t>>12&15]+HEX_CHARS[t>>8&15]+HEX_CHARS[t>>20&15]+HEX_CHARS[t>>16&15]+HEX_CHARS[t>>28&15]+HEX_CHARS[t>>24&15]+HEX_CHARS[s>>4&15]+HEX_CHARS[15&s]+HEX_CHARS[s>>12&15]+HEX_CHARS[s>>8&15]+HEX_CHARS[s>>20&15]+HEX_CHARS[s>>16&15]+HEX_CHARS[s>>28&15]+HEX_CHARS[s>>24&15]},Md5.prototype.toString=Md5.prototype.hex,Md5.prototype.digest=function(){this.finalize();var e=this.h0,r=this.h1,t=this.h2,s=this.h3;return[255&e,e>>8&255,e>>16&255,e>>24&255,255&r,r>>8&255,r>>16&255,r>>24&255,255&t,t>>8&255,t>>16&255,t>>24&255,255&s,s>>8&255,s>>16&255,s>>24&255]},Md5.prototype.array=Md5.prototype.digest,Md5.prototype.arrayBuffer=function(){this.finalize();var e=new ArrayBuffer(16),r=new Uint32Array(e);return r[0]=this.h0,r[1]=this.h1,r[2]=this.h2,r[3]=this.h3,e},Md5.prototype.buffer=Md5.prototype.arrayBuffer,Md5.prototype.base64=function(){for(var e,r,t,s="",a=this.array(),i=0;i<15;)e=a[i++],r=a[i++],t=a[i++],s+=BASE64_ENCODE_CHAR[e>>>2]+BASE64_ENCODE_CHAR[63&(e<<4|r>>>4)]+BASE64_ENCODE_CHAR[63&(r<<2|t>>>6)]+BASE64_ENCODE_CHAR[63&t];return e=a[i],s+=BASE64_ENCODE_CHAR[e>>>2]+BASE64_ENCODE_CHAR[e<<4&63]+"==",s};var exports=createMethod();COMMON_JS?module.exports=exports:(root.md5=exports,AMD&&(__WEBPACK_AMD_DEFINE_RESULT__=function(){return exports}.call(exports,__webpack_require__,exports,module),void 0===__WEBPACK_AMD_DEFINE_RESULT__||(module.exports=__WEBPACK_AMD_DEFINE_RESULT__)))})()}).call(this,__webpack_require__("6fcb"),__webpack_require__("dbbb"))},b342:function(e,r){(function(r){e.exports=r}).call(this,{})},bddd:function(e,r,t){"use strict";t.r(r);var s=function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("div",{staticClass:"animated fadeIn"},[t("section",{staticClass:"login-page"},[t("div",{staticClass:"login"},[t("div",{staticClass:"container"},[t("h3",[e._v("请注册 Register Here")]),t("p",{staticClass:"est"},[e._v("注册属于你的先锋账号 Register Your Pioneer Network Account"),t("br"),e._v("\n          已有账号？"),t("a",{attrs:{href:"#/login"},on:{click:e.goToLogin}},[e._v("前往登录!")])]),t("div",{staticClass:"login-form-grids"},[t("h5",[e._v("用户信息 Profile information")]),t("form",[t("input",{directives:[{name:"model",rawName:"v-model",value:e.regUser.lastName,expression:"regUser.lastName"},{name:"validate",rawName:"v-validate",value:"required",expression:"'required'"}],class:{"is-invalid":e.errors.has("name")},attrs:{type:"text",placeholder:"*姓 Last Name",name:"name",required:""},domProps:{value:e.regUser.lastName},on:{input:function(r){r.target.composing||e.$set(e.regUser,"lastName",r.target.value)}}}),t("input",{directives:[{name:"model",rawName:"v-model",value:e.regUser.firstName,expression:"regUser.firstName"},{name:"validate",rawName:"v-validate",value:"required",expression:"'required'"}],class:{"is-invalid":e.errors.has("name")},attrs:{type:"text",placeholder:"*名 First Name",name:"name",required:""},domProps:{value:e.regUser.firstName},on:{input:function(r){r.target.composing||e.$set(e.regUser,"firstName",r.target.value)}}}),t("div",{directives:[{name:"show",rawName:"v-show",value:e.errors.has("name"),expression:"errors.has('name')"}],staticClass:"invalid-tooltip"},[e._v(e._s(e.errors.first("name")))]),t("el-select",{staticStyle:{width:"50%"},attrs:{id:"year"},model:{value:e.info.year,callback:function(r){e.$set(e.info,"year",r)},expression:"info.year"}},e._l([2011,2012,2013,2014,2015,2016,2017,2018,2019,2020,2021,2022,2023,2024,2025],function(e){return t("el-option",{key:e,attrs:{label:e,value:e}})})),t("el-select",{staticStyle:{width:"49%"},model:{value:e.info.semester,callback:function(r){e.$set(e.info,"semester",r)},expression:"info.semester"}},e._l(e.infoList,function(e){return t("el-option",{key:e.value,attrs:{label:e.text,value:e.value}})})),t("el-select",{directives:[{name:"validate",rawName:"v-validate",value:"required",expression:"'required'"}],staticStyle:{width:"100%"},attrs:{id:"year1",placeholder:"性别 Gender"},model:{value:e.regUser.gender,callback:function(r){e.$set(e.regUser,"gender",r)},expression:"regUser.gender"}},e._l([{text:"男",value:"male"},{text:"女",value:"female"},{text:"未指定",value:"undetermined"}],function(e){return t("el-option",{key:e.value,attrs:{label:e.text,value:e.value}})})),t("div",{directives:[{name:"show",rawName:"v-show",value:e.errors.has("gender"),expression:"errors.has('gender')"}],staticClass:"invalid-tooltip"},[e._v(e._s(e.errors.first("gender")))]),t("el-date-picker",{staticStyle:{width:"100%"},attrs:{type:"date","prefix-icon":"none",format:"yyyy-MM-dd",placeholder:"生日 Birthday"},model:{value:e.regUser.birthday,callback:function(r){e.$set(e.regUser,"birthday",r)},expression:"regUser.birthday"}})],1),t("h5",[e._v("登录信息 Login information")]),t("form",[t("input",{directives:[{name:"validate",rawName:"v-validate",value:"email",expression:"'email'"},{name:"model",rawName:"v-model",value:e.regUser.email,expression:"regUser.email"}],attrs:{type:"email",placeholder:"Email",name:"email"},domProps:{value:e.regUser.email},on:{input:function(r){r.target.composing||e.$set(e.regUser,"email",r.target.value)}}}),t("div",{directives:[{name:"show",rawName:"v-show",value:e.errors.has("email"),expression:"errors.has('email')"}],staticClass:"invalid-tooltip"},[e._v(e._s(e.errors.first("email")))]),t("input",{directives:[{name:"validate",rawName:"v-validate",value:{required:!0,numeric:!0,min:5,max:11},expression:"{ required: true, numeric: true, min:5, max:11 }"},{name:"model",rawName:"v-model",value:e.regUser.qq,expression:"regUser.qq"}],class:{"is-invalid":e.errors.has("qq")},attrs:{type:"text",placeholder:"*QQ",name:"qq",required:""},domProps:{value:e.regUser.qq},on:{input:function(r){r.target.composing||e.$set(e.regUser,"qq",r.target.value)}}}),t("div",{directives:[{name:"show",rawName:"v-show",value:e.errors.has("qq"),expression:"errors.has('qq')"}],staticClass:"invalid-tooltip"},[e._v(e._s(e.errors.first("qq")))]),t("input",{directives:[{name:"model",rawName:"v-model",value:e.regUser.tel,expression:"regUser.tel"},{name:"validate",rawName:"v-validate",value:"required|numeric|min:8|max:13",expression:"'required|numeric|min:8|max:13'"}],class:{"is-invalid":e.errors.has("tel")},attrs:{type:"text",placeholder:"*电话号码",name:"tel",required:""},domProps:{value:e.regUser.tel},on:{input:function(r){r.target.composing||e.$set(e.regUser,"tel",r.target.value)}}}),t("div",{directives:[{name:"show",rawName:"v-show",value:e.errors.has("tel"),expression:"errors.has('tel')"}],staticClass:"invalid-tooltip"},[e._v(e._s(e.errors.first("tel")))]),t("input",{directives:[{name:"validate",rawName:"v-validate",value:"required|min:6|verify_password",expression:"'required|min:6|verify_password'"},{name:"model",rawName:"v-model",value:e.regUser.password,expression:"regUser.password"}],class:{"is-invalid":e.errors.has("password")},attrs:{type:"password",placeholder:"*密码",name:"password"},domProps:{value:e.regUser.password},on:{input:function(r){r.target.composing||e.$set(e.regUser,"password",r.target.value)}}}),t("div",{directives:[{name:"show",rawName:"v-show",value:e.errors.has("password"),expression:"errors.has('password')"}],staticClass:"invalid-tooltip"},[e._v(e._s(e.errors.first("password")))]),t("input",{directives:[{name:"validate",rawName:"v-validate",value:"required|min:6",expression:"'required|min:6'"},{name:"model",rawName:"v-model",value:e.newPwd,expression:"newPwd"}],class:{"is-invalid":e.errors.has("password")||e.notSame},attrs:{type:"password",name:"newPwd",placeholder:"*请再次输入密码"},domProps:{value:e.newPwd},on:{change:function(r){e.notSame=e.newPwd!==e.regUser.password},input:function(r){r.target.composing||(e.newPwd=r.target.value)}}}),t("div",{directives:[{name:"show",rawName:"v-show",value:e.notSame,expression:"notSame"}],staticClass:"invalid-tooltip"},[e._v("两次密码不一致")]),t("div",{directives:[{name:"show",rawName:"v-show",value:e.errors.has("newPwd"),expression:"errors.has('newPwd')"}],staticClass:"invalid-tooltip"},[e._v(e._s(e.errors.first("newPwd")))]),t("div",{staticClass:"register-check-box"},[t("div",{staticClass:"check"},[t("label",{staticClass:"checkbox"},[t("input",{directives:[{name:"model",rawName:"v-model",value:e.confirm,expression:"confirm"},{name:"validate",rawName:"v-validate",value:"required",expression:"'required'"}],attrs:{type:"checkbox",name:"checkbox"},domProps:{checked:Array.isArray(e.confirm)?e._i(e.confirm,null)>-1:e.confirm},on:{change:function(r){var t=e.confirm,s=r.target,a=!!s.checked;if(Array.isArray(t)){var i=null,o=e._i(t,i);s.checked?o<0&&(e.confirm=t.concat([i])):o>-1&&(e.confirm=t.slice(0,o).concat(t.slice(o+1)))}else e.confirm=a}}}),t("i"),e._v("确认上述信息并提交")])]),t("el-tooltip",{directives:[{name:"show",rawName:"v-show",value:e.errors.has("checkbox"),expression:"errors.has('checkbox')"}],staticClass:"item",attrs:{effect:"dark",placement:"top-start",content:e.errors.first("checkbox")}})],1),t("el-popover",{attrs:{placement:"top",width:"200",trigger:"manual"},model:{value:e.showModal,callback:function(r){e.showModal=r},expression:"showModal"}},[t("p",[e._v(e._s(e.msg))]),t("div",{staticStyle:{"text-align":"right",margin:"0"}},[t("el-button",{attrs:{type:"primary",size:"mini"},on:{click:e.goToLogin}},[e._v("确定")])],1),t("el-button",{staticStyle:{width:"100%"},attrs:{slot:"reference",type:"warning"},on:{click:e.doReg},slot:"reference"},[e._v("注册 Sign Up")])],1)],1)])])])])])},a=[],i=t("7f43"),o=t.n(i),n=t("897d"),l=t.n(n),d={name:"Register",data:function(){return{infoList:[{text:"春季加入先锋",value:"01"},{text:"秋季加入先锋",value:"02"},{text:"夏季加入先锋",value:"03"}],regUser:{info:"",lastName:"",firstName:"",email:"",password:"",qq:"",tel:"",birthday:"",gender:"",comment:""},isReturn:!1,info:{year:"2018",semester:"01"},newPwd:"",notSame:!1,showModal:!1,msg:"",succ:!1,fail:!1,headerBgVariant:"",confirm:!1}},methods:{doReg:function(){var e=this;this.$validator.validateAll().then(function(r){if(r){var t=e.regUser,s=e.info.year+"-"+e.info.semester;t.password=l()(e.regUser.password),t.info=s,o.a.post("/request/user/register",t).then(function(r){2001===r.data.code?(e.msg="申请成功！请等待审核通过。",e.headerBgVariant="success",e.showModal=!0):(e.msg=r.data.msg,e.headerBgVariant="danger",e.showModal=!0)})}})},goToLogin:function(){this.$router.push({path:"/login"})}}},c=d,h=t("048f"),u=Object(h["a"])(c,s,a,!1,null,null,null);u.options.__file="Register.vue";r["default"]=u.exports}}]);
//# sourceMappingURL=chunk-2341a04e.853c8816.js.map