console.log('tests script');
console.log('loaded tests');
var TestVM1 = (function () {
    function TestVM1() {
        depp.define({ 'vm1': '/api/UserVM.js' });
        depp.require(['vm1'], function () {
            QUnit.test("hello tests", function (assert) {
                TestVM1._done1 = assert.async();
                TestVM1._assert = assert;
                console.log('in test:');
                new UserVM(function (json) {
                    console.log(json);
                    _WDcb(json);
                    TestVM1._assert.ok(true);
                    TestVM1._done1();
                });
            });
        });
    }
    return TestVM1;
}());
var _WDcb;
function webDriverFoo(WDcb) {
    _WDcb = WDcb;
    console.log('start tests');
    var pro = loadQunit();
    pro.then(function () {
        QUnit.config.autostart = false;
        console.log('qunit loaded');
        new TestVM1();
    });
}
