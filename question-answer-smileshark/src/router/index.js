import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [

  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/main',
    name: 'name',
    component: () => import('../views/form/HomeFormView.vue'),
    children: [
      {
        path: '',
        name: 'home',
        component: () => import('../views/form/ShowContentView.vue')
      },
      {
        path: '/SelectTool',
        name: 'selectTool',
        component: () => import('../views/form/Tool/ToolSelectQuestionsView.vue')
      },
      {
        path: '/FinishTool',
        name: 'finishTool',
        component: () => import('../views/form/Tool/ToolFinishQuestionsView.vue')
      }, {
        path: '/CompleteDaily',
        name: 'completeDaily',
        component: () => import('../views/form/Tool/CompleteDailySummaryView.vue')
      }
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/form/LoginFormView.vue')
  }
  // {
  //   path: '/',
  //   name: 'login',
  //   component: () => import('../views/form/LoginFormView.vue'),
  //   meta: { requiresAuth: false }
  // },
  // {
  //   path: '/main',
  //   component: () => import('../views/form/HomeFormView.vue'),
  //   children: [

  //     {
  //       path: '/home',
  //       name: 'home',
  //       component: () => import('../views/form/ShowContentView.vue')
  //     },
  //     {
  //       path: '/SelectTool',
  //       name: 'selectTool',
  //       component: () => import('../views/form/ToolSelectQuestionsView.vue')
  //     }
  //   ]
  // }
  // {
  //   path: '/',
  //   name: 'main',
  //   component: HomeView
  // },
  // {
  //   path: '/',
  //   name: 'home',
  //   component: ShowContentView
  // },
  // {
  //   path: '/login',
  //   name: 'login',
  //   component: LoginView
  // },
  // {
  //   path: '/SelectTool',
  //   name: 'selectTool',
  //   component: ToolSelectQuestionsView
  // }
]

const router = new VueRouter({
  routes
})

export default router
