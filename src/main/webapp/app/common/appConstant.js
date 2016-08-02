//set global configuration of application and it can be accessed by injecting appSettings in any modules

app.constant('appSettings', {
	
	title: "Cancure", // app name
	lang: "en", // app default locale format
	dateFormat: "mm/dd/yy", // app date format
	baseURL: '/', // app service URL
	requestURL: {
		authRequest: 'oauth/token', // oauth request
		userRoles : 'roles', // fetching user roles
		createUser: 'user/save', // creating user
		userList: 'user/list' // listing out the user
	},
	theme: 'skin-yellow', // app default theme
	layout: "" // app default layout
		
});