console.log('tests script');
var pro = loadQunit();
pro.then(function () {
    console.log('qunit loaded');
    QUnit.start();
    new TestVM1();
});
var htmlUnitVar = null;
var TestVM1 = (function () {
    function TestVM1() {
        depp.define({ 'vm1': '/api/UserVM.js' });
        depp.require(['vm1']);
        QUnit.test("hello tests", function (assert) {
            TestVM1._done1 = assert.async();
            TestVM1._assert = assert;
            console.log('in test:');
            new UserVM(function (json) {
                console.log(json);
                htmlUnitVar = json;
                TestVM1._assert.ok(true);
                TestVM1._done1();
            });
        });
    }
    return TestVM1;
}());
