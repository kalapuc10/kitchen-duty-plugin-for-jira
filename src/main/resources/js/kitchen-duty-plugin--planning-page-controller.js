function initUserSearch(weekNumber) {
    // Init SOY template
    const planningPageWeekUsersTemplate = JIRA.Templates.KDP.planningPageWeekUsers({
        week: weekNumber
    });
    AJS.$('#kdp-planning-page-week-users-container').html(planningPageWeekUsersTemplate);

    // Init actions
    const auiUserSelectOptions = {
        ajax: {
            url: function () {
                return window.KDPrestUrl + '/user/search';
            },
            dataType: 'json',
            delay: 250,
            data: function (searchTerm) {
                return {query: searchTerm};
            },
            results: function (data) {
                return {results: data};
            },
            cache: true
        },
        minimumInputLength: 1,
        tags: 'true'
    };
    AJS.$('#kdp-user-select').auiSelect2(auiUserSelectOptions);

    // Load initial values from REST API and set for aui-select
    if (weekNumber !== null) {
        AJS.$.ajax({
            url: window.KDPrestUrl + '/planning/week/' + weekNumber + '/users',
            dataType: 'json',
            success: function (users) {
                const selectedUserList = [];
                if (users !== null) {
                    users.forEach(function (user) {
                        selectedUserList.push({id: user.username, text: user.username});
                    });
                    AJS.$('#kdp-user-select').select2('data', selectedUserList);
                }
            }
        });
    }

    // Save users on save button click
    AJS.$('#kdp-user-select-form').off(); // remove previous listeners
    AJS.$('#kdp-user-select-form').submit(function (e) {
        e.preventDefault();
        const selectedUserList = [];
        AJS.$(AJS.$('#kdp-user-select').select2('data')).each(function () {
            // we need to transform the JSON sent to the Endpoint since it
            // has to be in specific format
            selectedUserList.push({username: this.text});
        });
        AJS.$.ajax({
            url: window.KDPrestUrl + '/planning/week/' + weekNumber + '/users',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(selectedUserList),
            processData: false,
            success: function () {
                showSuccessFlag('Saved users for Week ' + weekNumber);
            },
            error: function () {
                showErrorFlag('Failed to save users for Week ' + weekNumber);
            }
        });
    });
}

function initWeekPicker() {
    // Init SOY template
    const planningPageWeekTemplate = JIRA.Templates.KDP.planningPageWeek();
    AJS.$('#kdp-planning-page-week-container').html(planningPageWeekTemplate);

    // Init actions
    AJS.$('#week-picker').off(); // remove previous listeners
    AJS.$('#week-picker').datePicker({'overrideBrowserDefault': true});
    AJS.$('#week-picker').change(function () {
        let week = moment(AJS.$('#week-picker').val()).week();
        initUserSearch(week);
    });
}

AJS.toInit(function () {
    AJS.log('KDP: Planning Page Controller initializing ...');
    const baseUrl = AJS.params.baseURL;
    window.KDPrestUrl = baseUrl + '/rest/kitchenduty/1.0';

    // set locale for moment-js so that week starts on sunday
    // and week numbers are correctly calculated
    moment.locale('en', {
        week: {
            dow: 0, // Sunday (0) is the first day of the week
            doy: 1  // Week that contains Jan 1st is the first week of the year.
        }
    });
    console.log('Week starts at: ' + moment().startOf('week').format('dddd'));
    console.log('Current moment locale: ' + moment().locale());

    // Init Base SOY template 
    const planningPageTemplate = JIRA.Templates.KDP.planningPage();
    AJS.$('#kdp-planning-page-container').html(planningPageTemplate);

    // Init child templates 
    initWeekPicker();
    initUserSearch(null);
});
