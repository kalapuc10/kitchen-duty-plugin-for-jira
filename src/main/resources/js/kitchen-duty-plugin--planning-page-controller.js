const showSuccessFlag = function (message) {
    require(['aui/flag'], function (flag) {
        var myFlag = flag({
            type: 'success',
            title: 'Kitchen Duty Plugin',
            close: 'auto',
            body: message
        });
    });
};

const initUserSearch = function (restUrl) {
    const templateUserSearch = JIRA.Templates.KDP.userSearch();

    const auiUserSelectOptions = {
        ajax: {
            url: restUrl + '/user/search',
            dataType: 'json',
            delay: 250,
            data: function (searchTerm) {
                return {
                    query: searchTerm
                };
            },
            results: function (data) {
                return {
                    results: data
                };
            },
            cache: true
        },
        minimumInputLength: 1,
        tags: 'true'
    };

    /* INIT TEMPLATES AND WIDGETS */

    AJS.$('#kdp-planning-page-container').append(templateUserSearch);
    AJS.$('#kdp-user-select').auiSelect2(auiUserSelectOptions);
    AJS.$('#kdp-user-select-form').submit(function (e) {
        e.preventDefault();
        AJS.$(AJS.$('#kdp-user-select').select2('data')).each(function () {
            showSuccessFlag(this.id);
        });
    });
};

AJS.toInit(function(){
    AJS.log('KDP: Planning Page Controller initializing ...');
    const baseUrl = AJS.params.baseURL;
    const restUrl = baseUrl + '/rest/kitchenduty/1.0';

    initUserSearch(restUrl);
});
