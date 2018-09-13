webpackJsonp([28],{406:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s=a(651),r=a(761),l=a(21),o=l(s.a,r.a,!1,null,null,null);e.default=o.exports},651:function(t,e,a){"use strict";var s=a(138),r=a.n(s),l=[],o=[{key:"index",label:"序号",class:"text-center"},{key:"lastName",label:"姓",sortable:!0},{key:"firstName",label:"名",sortable:!0},{key:"qq",label:"qq",sortable:!0},{key:"dorm",label:"宿舍号",sortable:!0},{key:"tel",label:"电话号码",sortable:!0}];e.a={name:"User",data:function(){return{field:o,currentPage:1,perPage:20,totalRows:0,pageOptions:[5,10,20,50],typeOptions:[{text:"教师",value:"f"},{text:"学生",value:"s"},{text:"系统管理",value:"a"},{text:"全部",value:""}],type:"",sortBy:"last_name",sortDesc:!1,filter:null,items:l,basePath:basePath}},computed:{sortOptions:function(){return this.field.filter(function(t){return t.sortable}).map(function(t){return{text:t.label,value:t.key}})}},watch:{type:function(){this.initTable()}},methods:{onFiltered:function(t){this.totalRows=t.length,this.currentPage=1},initTable:function(){this.$refs.userTable.refresh()},userTable:function(t){var e=this;this.isBusy=!0;var a="/user?type="+this.type+"&status=1&role=0&start="+t.currentPage+"&length="+t.perPage+"&orderCol=";switch(t.sortBy){case"lastName":a+="last_name";break;case"firstName":a+="first_name";break;default:a+=t.sortBy}return this.isNotEmpty(t.filter)&&(a+="&search="+t.filter),t.sortDesc?a+="&order=desc":a+="&order=asc",r.a.get(a).then(function(t){var a=t.data.data;return e.totalRows=t.data.recordsTotal,a||[]})},isNotEmpty:function(t){return""!==t&&void 0!==t&&null!==t}}}},761:function(t,e,a){"use strict";var s=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"animated fadeIn"},[a("b-row",[a("b-card",{attrs:{"header-tag":"header","footer-tag":"footer"}},[a("div",{attrs:{slot:"header"},slot:"header"},[a("i",{attrs:{className:"fa fa-align-justify"}}),a("strong",[t._v("通讯录")])]),t._v(" "),a("b-container",{attrs:{fluid:""}},[a("b-row",[a("b-col",{staticClass:"my-1",attrs:{md:"1"}},[a("legend",{staticClass:"col-form-legend"},[t._v("每页显示：")])]),t._v(" "),a("b-col",{staticClass:"my-1",attrs:{md:"1"}},[a("b-form-group",[a("b-form-select",{attrs:{options:t.pageOptions},model:{value:t.perPage,callback:function(e){t.perPage=e},expression:"perPage"}})],1)],1),t._v(" "),a("b-col",{staticClass:"my-1",attrs:{md:"1"}},[a("legend",{staticClass:"col-form-legend"},[t._v("用户类型：")])]),t._v(" "),a("b-col",{staticClass:"my-1",attrs:{md:"2"}},[a("b-form-group",[a("b-form-select",{attrs:{options:t.typeOptions},model:{value:t.type,callback:function(e){t.type=e},expression:"type"}})],1)],1),t._v(" "),a("b-col",{staticClass:"my-1",attrs:{md:"3"}},[a("b-form-group",[a("b-input-group",[a("b-input-group-button",[a("div",{staticClass:"mt-2"},[t._v("\n                    搜索：\n                  ")])]),t._v(" "),a("b-form-input",{model:{value:t.filter,callback:function(e){t.filter=e},expression:"filter"}}),t._v(" "),a("b-input-group-button",[a("b-button",{attrs:{variant:"danger",disabled:!t.filter},on:{click:function(e){t.filter=""}}},[t._v("重置")])],1)],1)],1)],1)],1),t._v(" "),a("b-table",{ref:"userTable",attrs:{"show-empty":"",stacked:"md",striped:!0,fixed:!0,hover:!0,items:t.userTable,fields:t.field,"current-page":t.currentPage,"per-page":t.perPage,filter:t.filter,"sort-by":t.sortBy,"sort-desc":t.sortDesc,isBusy:!1,small:!0},on:{"update:sortBy":function(e){t.sortBy=e},"update:sortDesc":function(e){t.sortDesc=e},filtered:t.onFiltered},scopedSlots:t._u([{key:"index",fn:function(e){return[t._v("\n            "+t._s((t.currentPage-1)*t.perPage+1+e.index)+"\n            "),t.isNotEmpty(e.item.profile)?a("img",{staticClass:"img-avatar",staticStyle:{width:"30px",height:"30px"},attrs:{src:t.basePath+"/static"+JSON.parse(e.item.profile).path}}):a("img",{staticClass:"img-avatar",staticStyle:{width:"30px",height:"30px"},attrs:{src:t.basePath+"/static/img/logo.png"}})]}}])}),t._v(" "),a("b-row",[a("b-col",{staticClass:"my-1",attrs:{md:"6"}},[a("b-pagination",{staticClass:"my-0",attrs:{"total-rows":t.totalRows,"per-page":t.perPage},model:{value:t.currentPage,callback:function(e){t.currentPage=e},expression:"currentPage"}})],1),t._v(" "),a("b-col",{staticClass:"my-1",attrs:{md:"6"}},[a("p",{staticClass:"text-muted",staticStyle:{"text-align":"right"}},[t._v(" 显示 "+t._s((t.currentPage-1)*t.perPage+1)+" 至\n              "+t._s((t.currentPage-1)*t.perPage+t.perPage<=t.totalRows?(t.currentPage-1)*t.perPage+t.perPage:t.totalRows)+" 条 ，总共 "+t._s(t.totalRows)+" 条数据 ")])])],1)],1)],1)],1)],1)},r=[],l={render:s,staticRenderFns:r};e.a=l}});
//# sourceMappingURL=28.319bb050ac05fb37f7a8.js.map