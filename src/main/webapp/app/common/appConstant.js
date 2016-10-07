//set global configuration of application and it can be accessed by injecting appSettings in any modules

app.constant('appSettings', {

	title: "Cancure", // app name
	lang: "en", // app default locale format
	dateFormat: "mm/dd/yy", // app date format
	baseURL: '', // app service URL
	requestURL: {
		authRequest: 'oauth/token', // oauth request
		userRoles : 'roles', // fetching user roles
		createUser: 'user/save', // creating user
		userList: 'user/list', // listing out the user
		patientRegistration: 'patientregistration/patient/save', // patient registration
		patientRegDrpDwn : 'common/lovs/FamilyRelation_BloodGroups_MaritalStatus_EmploymentStatus_KnowAboutCancure_RecommendationType_IDProof_IncomeProof_TypeOfSupportSought', // to get dropdown values for patient reg page
		doctorList : 'doctor/list', // to fetch doctor list
		hospitalList : 'hospital/list', // to fetch hospital list
		hpocList : 'user/hpoc', // to fetch hpoc list
		hpocMapping : 'link/hpoc/hospital', // to map hpoc with hospital
		hospitalHpoc : 'listAll/hpoc/hospital', //list all hospital and hpoc
		hpocDoctors : 'doctor/hpoclist', // to get all doctors under that HPOC
		myQueue : 'tasks/my', // to fetch tasks in myQueue
		resetPassword : 'user/resetpassword', // to reset password
		logout : 'logout' // logout service
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
	}, {
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
	}],

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
	}],

	menuList : [{
		title: "Home",
		icon: "home",
		state: "home",
		pageId : 0
	},
	{
		title: "User",
		icon: "users",
		state: "manageUser.createUser",
		pageId : 1
	},
	{
		title: "Patient Registration",
		icon: "pencil-square-o",
		state: "patientRegistration",
		pageId : 2
	},
	{
		title: "Hospitals",
		icon: "hospital-o",
		state: "hospital.hospitalList",
		pageId : 3
	},
	{
		title: "Doctors",
		icon: "user-md",
		state: "doctor.doctorList",
		pageId : 4
	},
	{
		title: "Patient Search",
		icon: "search",
		state: "searchPatient",
		pageId : 5
	},
	{
		title: "Settings",
		icon: "gears",
		state: "settingsList",
		pageId : 6
	}],
	
	pageAccess : {
		ROLE_GUEST : [0,5],
		ROLE_SECRETARY : [0, 5],
		ROLE_EXECUTIVE_COMMITTEE : [0,5],
		ROLE_ADMIN : [0, 1, 3, 4, 5, 6],
		ROLE_HOSPITAL_POC : [0,5],
		ROLE_DOCTOR : [0,5],
		ROLE_PROGRAM_COORDINATOR : [0, 2, 5]
	},
	
	rolesList : [] // stores all role names (authority)
});