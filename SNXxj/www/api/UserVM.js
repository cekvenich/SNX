console.log('User VM');
var UserVM = (function () {
    function UserVM(cb) {
        fetch('http://localhost:8888/API1')
            .then(function (response) {
            return response.json();
        })
            .then(function (myJson) {
            cb(myJson);
        });
    }
    return UserVM;
}());
