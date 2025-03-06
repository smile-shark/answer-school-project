import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/form/HomeFormView.vue'
import ShowContentView from '../views/form/ShowContentView.vue'
import LoginView from '../views/form/LoginFormView.vue'
import ToolSelectQuestionsView from '../views/form/Tool/ToolSelectQuestionsView.vue'
import ToolFinishQuestionsView from '../views/form/Tool/ToolFinishQuestionsView.vue'
import CompleteDailySummaryView from '../views/form/Tool/CompleteDailySummaryView.vue'

Vue.use(VueRouter)

const routes = [

  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/main',
    name: 'name',
    component: HomeView,
    children: [
      {
        path: '',
        name: 'home',
        component: ShowContentView
      },
      {
        path: '/SelectTool',
        name: 'selectTool',
        component: ToolSelectQuestionsView
      },
      {
        path: '/FinishTool',
        name: 'finishTool',
        component: ToolFinishQuestionsView
      }, {
        path: '/CompleteDaily',
        name: 'completeDaily',
        component: CompleteDailySummaryView
      }
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  }
]

const router = new VueRouter({
  routes
})

export default router
