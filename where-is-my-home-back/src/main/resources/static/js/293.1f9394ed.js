"use strict";(self["webpackChunkwhere_is_my_home_front"]=self["webpackChunkwhere_is_my_home_front"]||[]).push([[293],{4293:function(t,e,r){r.r(e),r.d(e,{default:function(){return u}});var s=function(){var t=this,e=t._self._c;return e("b-container",{staticClass:"bv-example-row mt-3"},[e("b-row",[e("b-col",[e("b-alert",{attrs:{show:""}},[e("h3",[t._v("글작성")])])],1)],1),e("board-input-item",{attrs:{type:"register"}})],1)},n=[],o=r(3688),a={name:"BoardWrite",components:{BoardInputItem:o.Z}},i=a,c=r(1001),l=(0,c.Z)(i,s,n,!1,null,null,null),u=l.exports},3688:function(t,e,r){r.d(e,{Z:function(){return f}});var s=function(){var t=this,e=t._self._c;return e("b-row",{staticClass:"mb-1"},[e("b-col",{staticStyle:{"text-align":"left"}},[e("b-form",{on:{submit:t.onSubmit,reset:t.onReset}},[e("b-form-group",{attrs:{id:"title-group",label:"제목:","label-for":"title",description:"제목을 입력하세요."}},[e("b-form-input",{attrs:{id:"title",type:"text",required:"",placeholder:"제목 입력..."},model:{value:t.article.title,callback:function(e){t.$set(t.article,"title",e)},expression:"article.title"}})],1),e("b-form-group",{attrs:{id:"content-group",label:"내용:","label-for":"content"}},[e("b-form-textarea",{attrs:{id:"content",placeholder:"내용 입력...",rows:"10","max-rows":"15"},model:{value:t.article.content,callback:function(e){t.$set(t.article,"content",e)},expression:"article.content"}})],1),"register"===this.type?e("b-button",{staticClass:"m-1",attrs:{type:"submit",variant:"primary"}},[t._v("글작성")]):e("b-button",{staticClass:"m-1",attrs:{type:"submit",variant:"primary"}},[t._v("글수정")]),e("b-button",{staticClass:"m-1",attrs:{type:"reset",variant:"danger"}},[t._v("초기화")])],1)],1)],1)},n=[],o=(r(7658),r(6441)),a=r(408);const i="userStore";var c={name:"BoardInputItem",data(){return{article:{id:0,userId:-1,title:"",content:""}}},props:{type:{type:String}},created(){if("modify"===this.type){let t=this.$route.params.id;(0,o.fq)(t,(({data:t})=>{this.article=t}),(t=>{console.log(t)}))}},methods:{...(0,a.nv)(i,["tokenRegeneration","userLogout"]),onSubmit(t){t.preventDefault();let e=!0,r="";e&&!this.article.title&&(r="제목 입력해주세요",e=!1,this.$refs.title.focus()),e&&!this.article.content&&(r="내용 입력해주세요",e=!1,this.$refs.content.focus()),e?"register"===this.type?this.registArticle():this.modifyArticle():alert(r)},onReset(t){t.preventDefault(),this.article.id=0,this.article.title="",this.article.content="",this.moveList()},registArticle(){let t={title:this.article.title,content:this.article.content};""!==this.article.content?confirm("작성 하시겠습니까?")&&(0,o.hY)(t,(t=>{let e="등록 처리시 문제가 발생했습니다.";200===t.status&&(e="등록이 완료되었습니다."),alert(e),this.moveList()}),(async e=>{console.log(e),400===e.response.status?alert("잘못된 접근입니다."):401===e.response.status&&(console.log("글작성: 권한이 없습니다.. 토큰 재발행으로 go"),await this.tokenRegeneration(),(0,o.hY)(t,(t=>{let e="등록 처리시 문제가 발생했습니다.";200===t.status&&(e="등록이 완료되었습니다."),alert(e),this.moveList()}),(t=>{console.log(t),400===t.response.status?alert("잘못된 접근입니다."):401===t.response.status&&(alert("인증이 만료되었습니다. 다시 로그인 해 주세요"),this.userLogout())})))})):alert("내용을 입력하세요!")},modifyArticle(){let t={id:this.article.id,title:this.article.title,content:this.article.content};""!==this.article.content?confirm("수정 하시겠습니까?")&&(0,o.cn)(t,(t=>{let e="수정 처리시 문제가 발생했습니다.";200===t.status&&(e="수정이 완료되었습니다."),alert(e),this.moveList()}),(async e=>{console.log(e),400===e.response.status?alert("잘못된 접근입니다."):401===e.response.status&&(await this.tokenRegeneration(),(0,o.cn)(t,(t=>{let e="수정 처리시 문제가 발생했습니다.";200===t.status&&(e="수정이 완료되었습니다."),alert(e),this.moveList()}),(t=>{console.log(t),400===t.response.status?alert("잘못된 접근입니다."):401===t.response.status&&(alert("인증이 만료되었습니다. 다시 로그인 해 주세요"),this.userLogout())})))})):alert("내용을 입력하세요!")},moveList(){this.$router.push({name:"boardlist"})}}},l=c,u=r(1001),h=(0,u.Z)(l,s,n,!1,null,null,null),f=h.exports},6441:function(t,e,r){r.d(e,{D9:function(){return u},Ir:function(){return h},YF:function(){return m},cn:function(){return c},fq:function(){return i},hY:function(){return a},jC:function(){return o},jX:function(){return l},li:function(){return f},p5:function(){return d}});var s=r(2243);const n=(0,s.b)();function o(t,e,r){n.get("/board",{params:t}).then(e).catch(r)}function a(t,e,r){n.defaults.headers["Authorization"]="Bearer "+sessionStorage.getItem("access-token"),n.post("/board/write",JSON.stringify(t)).then(e).catch(r)}function i(t,e,r){n.defaults.headers["Authorization"]="Bearer "+sessionStorage.getItem("access-token"),n.get(`/board/${t}`).then(e).catch(r)}function c(t,e,r){n.defaults.headers["Authorization"]="Bearer "+sessionStorage.getItem("access-token"),n.put("/board",JSON.stringify(t)).then(e).catch(r)}function l(t,e,r){n.defaults.headers["Authorization"]="Bearer "+sessionStorage.getItem("access-token"),n.delete(`/board/${t}`).then(e).catch(r)}function u(t,e,r){n.get("/board/length",{params:t}).then(e).catch(r)}function h(t,e,r){n.defaults.headers["Authorization"]="Bearer "+sessionStorage.getItem("access-token"),n.post("/board/comment",JSON.stringify(t)).then(e).catch(r)}function f(t,e,r){n.get(`/board/${t}/comment`).then(e).catch(r)}function m(t,e,r){n.defaults.headers["Authorization"]="Bearer "+sessionStorage.getItem("access-token"),n.delete(`/board/comment/${t}`).then(e).catch(r)}function d(t,e,r){n.defaults.headers["Authorization"]="Bearer "+sessionStorage.getItem("access-token"),n.put("/board/comment",JSON.stringify(t)).then(e).catch(r)}}}]);
//# sourceMappingURL=293.1f9394ed.js.map