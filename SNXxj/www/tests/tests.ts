
console.log('tests script')

declare var QUnit
declare var loadQunit
declare var depp
declare var UsersVM

console.log('loaded tests')

class TestVM1 {
   static _done1
   static _assert
   constructor () {
      depp.define({'vm1':'/api/UserVM.js'})
      depp.require(['vm1'], function(){

      QUnit.test( "hello tests", function( assert ) {
         TestVM1._done1 = assert.async()
         TestVM1._assert = assert
         console.log('in test:')
         new UserVM(function(json){
            console.log(json)
            _WDcb(json)
            TestVM1._assert.ok(true) //passed. Should check json.
            TestVM1._done1()
         })
      })//tests

      })//req
   }//()
   
}//class

// setup the webdrive CB
var _WDcb
function webDriverFoo(WDcb) {
   _WDcb = WDcb
   console.log('start tests')
   var pro = loadQunit()
   pro.then(function(){
      QUnit.config.autostart = false

      console.log('qunit loaded')
   
      //QUnit.start()
      new TestVM1()
   })//pro
}



