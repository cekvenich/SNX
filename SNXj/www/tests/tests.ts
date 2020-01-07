
console.log('tests script')

declare var QUnit
declare var loadQunit
declare var depp
declare var UsersVM

var pro = loadQunit()
pro.then(function(){
   console.log('qunit loaded')

   QUnit.start()
   new TestVM1()
})//pro

class TestVM1 {
   static _done1
   static _assert
   constructor () {
      depp.define({'vm1':'/api/UserVM.js'})
      depp.require(['vm1'])

      QUnit.test( "hello tests", function( assert ) {
         TestVM1._done1 = assert.async()
         TestVM1._assert = assert
         console.log('in test:')
         new UserVM(function(json){
            console.log(json)
            TestVM1._assert.ok(true) //passed. Should check json.
            TestVM1._done1()
         })
      })
   }//()
   
}//class


