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
	themeList : [{
			theme: "purple",
			color: "skin-purple",
			title: "Dark - Purple Skin",
			icon: ""
		}, {
			theme: "black",
			color: "skin-black",
			title: "Dark - Black Skin",
			icon: ""
		}, {
			theme: "blue",
			color: "skin-blue",
			title: "Dark - Blue Skin",
			icon: ""
		},
		{
			theme: "green",
			color: "skin-green",
			title: "Dark - Green Skin",
			icon: ""
		}, {
			theme: "yellow",
			color: "skin-yellow",
			title: "Dark - Yellow Skin",
			icon: ""
		}, {
			theme: "red",
			color: "skin-red",
			title: "Dark - Red Skin",
			icon: ""
		}
	],
	
	layout: "fixed", // app default layout
	layoutList : [{
			name: "Boxed",
			layout: "layout-boxed"
		},		{
			name: "Fixed",
			layout: "fixed"
		},		{
			name: "Sidebar Collapse",
			layout: "sidebar-collapse"
		}
	],
	
	menuList : [{
			title: "Home",
			icon: "dashboard",
			state: "home"
		},
		{
			title: "Create User",
			icon: "gears",
			state: "createUser"
		},
		{
			title: "User List",
			icon: "phone",
			state: "userList"
		},
		{
			title: "Search List",
			icon: "phone",
			state: "searchUser"
		}
	]

});