Vue.use(VueRouter)


const router = new VueRouter({
	mode:'history',
			routes:[
				{
					path:'/LxcBlog_FrontWeb*',
					meta:{
						requireAuth: true
					}
				}
			]
		})
		
router.beforeEach((to,from,next)=>{
	next();
})