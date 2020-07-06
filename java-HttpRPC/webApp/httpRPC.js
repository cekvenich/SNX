
class HttpRPC {

    constructor(httpOrs, host, port) {
        this.DEBUG = false;
        this.httpOrs = httpOrs;
        this.host = host;
        this.port = port;
    }

    enc(obj) {
        const someBytes = JSON.stringify(obj); // js
        // B64
        const encoded = btoa(someBytes);
        return encoded
    }
    
    dec(dat) {
        console.log(dat) // fetch converts from array buffer
        var deco = atob(dat)

        const obj = JSON.parse(deco)
        return obj
    }

    invoke(route, method, params, timeout) {
        if (!timeout)
            timeout = 20000;
        if (!params)
            params = {};
        params.method = method;
     
        params.view = window.location.href;

        const THIZ = this;
        const url = new URL(THIZ.httpOrs + '://' + THIZ.host + ':' + THIZ.port + '/' + route);
        console.log(url.toString())

        const pro1 = new Promise(function (resolve, reject) {
            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'text/plain'
                },
                mode: 'cors',
                keepalive: true,
                body: THIZ.enc(params)
            })
            .then(function (fullResp) {
                console.log("back")
                if (!fullResp.ok) {
                    console.warn('HTTP protocol error in RPC: ' + fullResp.status + fullResp);
                    reject('HTTP protocol error in RPC: ' + fullResp.status + fullResp);
                }
                else
                    return fullResp.arrayBuffer();
            })
            .then(function (ab) {
                var str = new TextDecoder().decode(ab);
                var resp = THIZ.dec(str);
                if ((!resp) || resp.errorMessage) {
                    console.info(method + ' ' + str);
                    reject(method + ' ' + str);
                }
                console.log(resp)
                resolve(resp);
            })
            .catch(function (err) {
                console.warn('fetch err ', method, err);
                reject(method + ' ' + err);
            });
            
        }); // pro 1
        const pro2 = new Promise(function (resolve, reject) {
            setTimeout(() => reject('timeout'), timeout);
        });
        return Promise.race([pro1, pro2]);
    }

} // class

export { HttpRPC }


