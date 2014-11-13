hrApp.controller('EmployeeAddController', ['$scope', '$http', '$location', function ($scope, $http, $location) {
    $scope.employee = {};

    $http({url: 'http://localhost:8085/app/mvc/departments/findAll', method: 'GET'}).
        success(function (data) {
            $scope.departments = data;
        });
    $http({url: 'http://localhost:8085/app/mvc/employees/findAll', method: 'GET'}).
        success(function (data) {
            $scope.managers = data;
        });

    $http({url: 'http://localhost:8085/app/mvc/jobs/findAll', method: 'GET'}).
        success(function (data) {
            $scope.jobs = data;
        });

    /**
     * Reset form
     */
    $scope.reset = function () {
        this.employee = {};
    };

    /**
     * Persist an employee
     * @param addEmployee - employee to be persisted
     */
    $scope.create = function (addEmployee) {
        $http({url: 'http://localhost:8085/app/mvc/employees/create', method: 'PUT',data:addEmployee}).
            success(function (data) {
                $scope.employee = data;
                $location.url('/employeeview/'+$scope.employee.employeeId);
            });
    }
}]);