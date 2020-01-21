
console.log('User VM')

/**
 * As a model, this should match the User View
 */
class UserVM {
   constructor(cb) {
      fetch('http://localhost:8888/API1')
      .then((response) => {
        return response.json()
      })
      .then((myJson) => {
       cb(myJson)
      })
   }
}//class
