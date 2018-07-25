import Vue from 'vue'
import Router from 'vue-router'

const _import = require('./_import_' + process.env.NODE_ENV)
// in development env not use Lazy Loading,because Lazy Loading too many pages will cause webpack hot update too slow.
// so only in production use Lazy Loading

Vue.use(Router);

/* layout */
import Layout from '../views/layout/Layout'

/**
 * icon : the icon show in the sidebar
 * hidden : if `hidden:true` will not show in the sidebar
 * redirect : if `redirect:noredirect` will no redirct in the levelbar
 * noDropdown : if `noDropdown:true` will has no submenu
 * meta : { role: ['admin'] }  will control the page role
 **/
export const constantRouterMap = [
  {path: '/login', component: _import('login/index'), hidden: true},
  {path: '/authredirect', component: _import('login/authredirect'), hidden: true},
  {path: '/404', component: _import('errorPage/404'), hidden: true},
  {path: '/401', component: _import('errorPage/401'), hidden: true},
  {
    path: '/error',
    component: Layout,
    redirect: 'noredirect',
    name: '错误页面',
    icon: '404',
    hidden: true,
    children: [
      {path: '401', component: _import('errorPage/401'), name: '401'},
      {path: '404', component: _import('errorPage/404'), name: '404'}
    ]
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: '首页',
    hidden: true,
    children: [{path: 'dashboard', component: _import('dashboard/index')}]
  },
  {
    path: '',
    component: Layout,
    redirect: '/introduction/index',
    icon: 'people',
    noDropdown: true,
    children: [{path: '/introduction/index', component: _import('introduction/index'), name: '简述'}]
  }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({y: 0}),
  routes: constantRouterMap
})

export const asyncRouterMap = [
  {
    path: '',
    component: Layout,
    redirect: 'noredirect',
    name: '系统管理',
    icon: 'table',
    children: [
      {
        path: '/admin/user',
        // 如果不配置，则从后台读取
        component: _import('admin/user/index'),
        name: '用户管理',
        icon: 'user'
      } /* ,
      {
        path: '/admin/uLayout',
        // 如果不配置，则从后台读取
        component: _import('admin/user/UserLayout'),
        name: '用户管理新增修改',
        icon: 'user',
        hidden: true
      } , {
        path: '/admin/org',
        component: _import('admin/org/index'),
        name: '组织机构管理',
        icon: 'people'
      }, {
        path: '/admin/menu',
        component: _import('admin/menu/index'),
        name: '菜单管理',
        icon: 'people'
      }, {
        path: '/admin/role',
        component: _import('admin/role/index'),
        name: '角色管理',
        icon: 'user'
      }, {
        path: '/admin/personalRole',
        component: _import('admin/personalRole/index'),
        name: '人员角色管理',
        icon: 'user'
      }, {
        path: '/admin/auth',
        component: _import('admin/role/auth'),
        name: '授权管理',
        icon: 'people'
      }, {
        path: '/admin/dict',
        component: _import('admin/dict/index'),
        name: '字典管理',
        icon: 'user'
      }, {
        path: '/admin/temp',
        component: _import('admin/temp/index'),
        name: '模版生成',
        icon: 'user'
      }, {
        path: '/admin/weekday',
        component: _import('admin/weekday/index'),
        name: '工作日管理',
        icon: 'user'
      }
      */
      // , {
      //   path: '/introduction/introduction',
      //   // 如果不配置，则从后台读取
      //   component: _import('introduction/introduction'),
      //   name: '系统介绍',
      //   icon: 'user'
      // }
    ]
  },
  /*
  {
    path: '',
    component: Layout,
    redirect: 'noredirect',
    name: '系统监控',
    icon: 'table',
    children: [
      {
        path: '/monitor/registry',
        name: '服务注册中心',
        icon: 'user'
      }
    ]
  },
  {
    path: '',
    component: Layout,
    redirect: 'noredirect',
    name: '应用管理',
    icon: 'table',
    children: [
      {
        path: '/admin/server',
        component: _import('admin/server/index'),
        name: '服务管理',
        icon: 'user'
      }, {
        path: '/admin/application',
        component: _import('admin/application/index'),
        name: '应用管理',
        icon: 'user'
      }
    ]
  },
  */
  /*
  {
    path: '',
    component: Layout,
    redirect: 'noredirect',
    name: '零点餐厅管理',
    icon: 'table',
    children: [
      {
        path: '/food/detail',
        component: _import('food/detail/index'),
        name: '菜品管理',
        icon: 'user'
      }, {
        path: '/food/order',
        component: _import('food/order/index'),
        name: '订单管理',
        icon: 'user'
      }
    ]
  },
  {
    path: '',
    component: Layout,
    redirect: 'noredirect',
    name: '主熟食管理',
    icon: 'table',
    children: [
      {
        path: '/cooked/detail',
        component: _import('cooked/detail/index'),
        name: '主熟食管理',
        icon: 'user'
      },
      {
        path: '/cooked/order',
        component: _import('cooked/order/index'),
        name: '订单管理',
        icon: 'user'
      },
      {
        path: '/cooked/num',
        component: _import('cooked/num/index'),
        name: '数量管理',
        icon: 'user'
      }
      // }, {
      //   path: '/cooked/order',
      //   component: _import('cooked/order/index'),
      //   name: '订单管理',
      //   icon: 'user'
      // }
    ]
  },
  */
  {
    path: '',
    component: Layout,
    redirect: '/permission/index',
    name: '权限测试',
    icon: 'lock',
    meta: {role: ['admin']},
    noDropdown: true,
    children: [{
      path: '/permission/index',
      component: _import('permission/index'),
      name: '权限测试页',
      meta: {role: ['admin']}
    }]
  },
  {
    path: '',
    component: _import('samples/index'),
    name: 'Samples',
    icon: 'example',
    children: [
      {
        path: '',
        component: Layout,
        icon: 'icon',
        noDropdown: true,
        children: [{path: '/icon/index', component: _import('samples/svg-icons/index'), name: 'icons'}]
      },
      {
        path: '',
        component: Layout,
        redirect: '/components/index',
        name: '组件',
        icon: 'component',
        children: [
          {path: '/components/index', component: _import('samples/components/index'), name: '介绍 '},
          {path: '/components/tinymce', component: _import('samples/components/tinymce'), name: '富文本编辑器'},
          {path: '/components/markdown', component: _import('samples/components/markdown'), name: 'Markdown'},
          {path: '/components/jsoneditor', component: _import('samples/components/jsonEditor'), name: 'JSON编辑器'},
          {path: '/components/dndlist', component: _import('samples/components/dndList'), name: '列表拖拽'},
          {path: '/components/splitpane', component: _import('samples/components/splitpane'), name: 'SplitPane'},
          {path: '/components/avatarupload', component: _import('samples/components/avatarUpload'), name: '头像上传'},
          {path: '/components/dropzone', component: _import('samples/components/dropzone'), name: 'Dropzone'},
          {path: '/components/sticky', component: _import('samples/components/sticky'), name: 'Sticky'},
          {path: '/components/countto', component: _import('samples/components/countTo'), name: 'CountTo'},
          {path: '/components/mixin', component: _import('samples/components/mixin'), name: '小组件'},
          {path: '/components/backtotop', component: _import('samples/components/backToTop'), name: '返回顶部'}
        ]
      },
      {
        path: '',
        component: Layout,
        redirect: '/charts/index',
        name: '图表',
        icon: 'chart',
        children: [
          {path: '/charts/index', component: _import('samples/charts/index'), name: '介绍'},
          {path: '/charts/keyboard', component: _import('samples/charts/keyboard'), name: '键盘图表'},
          {path: '/charts/keyboard2', component: _import('samples/charts/keyboard2'), name: '键盘图表2'},
          {path: '/charts/line', component: _import('samples/charts/line'), name: '折线图'},
          {path: '/charts/mixchart', component: _import('samples/charts/mixChart'), name: '混合图表'}
        ]
      },
      {
        path: '',
        component: Layout,
        redirect: 'noredirect',
        name: '综合实例',
        icon: 'example',
        children: [
          {
            path: '',
            component: _import('samples/index'),
            redirect: '/example/table/table',
            name: 'Table',
            icon: 'table',
            children: [
              {
                path: '/example/table/dynamictable',
                component: _import('samples/example/table/dynamictable/index'),
                name: '动态table'
              },
              {path: '/example/table/dragtable', component: _import('samples/example/table/dragTable'), name: '拖拽table'},
              {
                path: '/example/table/inline_edit_table',
                component: _import('samples/example/table/inlineEditTable'),
                name: 'table内编辑'
              },
              {path: '/example/table/table', component: _import('samples/example/table/table'), name: '综合table'}
            ]
          },
          {
            path: '/example/form/edit',
            icon: 'form',
            component: _import('samples/example/form'),
            name: '编辑Form',
            meta: {isEdit: true}
          },
          {path: '/example/form/create', icon: 'form', component: _import('samples/example/form'), name: '创建Form'},
          {path: '/tab/index', icon: 'tab', component: _import('samples/example/tab/index'), name: 'Tab'}
        ]
      },
      {
        path: '',
        component: Layout,
        redirect: '/errlog/log',
        name: 'errlog',
        icon: 'bug',
        noDropdown: true,
        children: [{path: '/errlog/log', component: _import('samples/errlog/index'), name: '错误日志'}]
      },
      {
        path: '',
        component: Layout,
        redirect: '/excel/download',
        name: 'excel',
        icon: 'excel',
        children: [
          {path: '/excel/download', component: _import('samples/excel/index'), name: 'export excel'},
          {path: '/excel/download2', component: _import('samples/excel/selectExcel'), name: 'export selected'},
          {path: '/excel/upload', component: _import('samples/excel/uploadExcel'), name: 'upload excel'}
        ]
      },
      {
        path: '',
        component: Layout,
        redirect: '/zip/download',
        name: 'zip',
        icon: 'zip',
        children: [
          {path: '/zip/download', component: _import('samples/zip/index'), name: 'export zip'}
        ]
      },
      {
        path: '',
        component: Layout,
        redirect: 'noredirect',
        name: 'theme',
        icon: 'theme',
        noDropdown: true,
        children: [{path: '/theme/index', component: _import('samples/theme/index'), name: '换肤'}]
      },
      {
        path: '',
        component: Layout,
        redirect: 'noredirect',
        icon: 'clipboard',
        noDropdown: true,
        children: [{path: '/clipboard/index', component: _import('samples/clipboard/index'), name: 'clipboard'}]
      }
    ]
  },
  {path: '*', redirect: '/error/404', hidden: true}
];
