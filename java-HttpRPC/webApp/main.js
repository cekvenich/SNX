console.log('hi')
import { HttpRPC } from './httpRPC.js'

const rpc = new HttpRPC('http', 'localhost', 8888)

var code = rpc.enc("oh hi")
console.log(rpc.dec(code))


rpc.invoke('api', 'multiply', {a:5, b:2}, 600 )
   .then(function(resp) {
      console.log(resp)
   })

