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
		firstLogResetPassword: 'user/firstlogin/save', //first login reset password
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
		forgotPassword : 'user/forgotpassword', // to reset password on forgot password scenario
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
		state: "doctor",
		pageId : 4
	},
	{
		title: "Pharmacy",
		icon: "medkit",
		state: "",
		pageId : 5
	},

	{
		title: "Lab",
		icon: "flask",
		state: "",
		pageId : 6
	},
	{
		title: "Patient Search",
		icon: "search",
		state: "searchPatient",
		pageId : 7
	},
	{
		title: "Settings",
		icon: "gears",
		state: "settingsList",
		pageId : 8
	},
	{
		title: "Patient Visit",
		icon: "gears",
		state: "patientHospitalVisit",
		pageId : 9
	},
	{
		title: "Pharmacy Dispatch",
		icon: "gears",
		state: "pharmacyDispatch",
		pageId : 10
	}],
	
	pageAccess : {
		ROLE_GUEST : [0, 7],
		ROLE_SECRETARY : [0, 7],
		ROLE_EXECUTIVE_COMMITTEE : [0, 7],
		ROLE_ADMIN : [0, 1, 3, 4, 5, 6, 7, 8],
		ROLE_HOSPITAL_POC : [0, 2, 7, 9],
		ROLE_DOCTOR : [0, 7],
		ROLE_PROGRAM_COORDINATOR : [0, 2, 7, 10],
		ROLE_PHARMACY : [10, 7]
	},
	
	rolesList : [] // stores all role names (authority)
});