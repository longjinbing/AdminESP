import router from './router';
import store from './store';
import NProgress from 'nprogress'; // Progress 进度条
import 'nprogress/nprogress.css'; // Progress 进度条样式
import { getToken } from '@/utils/auth'; // 验权
// import { Message } from 'element-ui';
// permissiom judge
/*
function hasPermission(roles, permissionRoles) {
  if (roles.indexOf('admin') >= 0) return true // admin权限 直接通过
  if (!permissionRoles) return true
  return roles.some(role => permissionRoles.indexOf(role) >= 0)
}
*/

// register global progress.
const whiteList = ['/login', '/authredirect']; // 不重定向白名单
router.beforeEach((to, from, next) => {
  NProgress.start(); // 开启Progress
  if (getToken()) { // 判断是否有token
    if (to.path === '/login') {
      next({path: '/'});
    } else {
      console.log('角色的数量' + store.getters.roles.length);
      // if (store.getters.roles.length === 0) { // 判断当前用户是否已拉取完user_info信息
      if (store.getters.addRouters.length === 0) { // 判断当前用户是否已拉取完user_info信息
        /*
        store.dispatch('GetUserInfo').then(res => { // 拉取user_info
          if (res.status === 308010) {
            Message({ showClose: true, message: res.data, type: 'error' });
            return false;
          }
          const roles = res.data.roles;
          const menus = res.data.menus;
          if (roles.length === 0) {
            // 该用户未分配角色
            NProgress.done();
            Message({ showClose: true, message: '该用户未授权，请联系管理员授权', type: 'error' });
            return false;
          }
          store.dispatch('GenerateRoutes', {menus, roles}).then(() => { // 生成可访问的路由表
            router.addRoutes(store.getters.addRouters); // 动态添加可访问路由表
            next({...to}); // hack方法 确保addRoutes已完成
          });
        }).catch(() => {
          store.dispatch('FedLogOut').then(() => next({path: '/login'}));
        });
        */
        store.dispatch('GenerateRoutesMock').then(() => { // 生成可访问的路由表
          console.debug(store.getters.addRouters)
          router.addRoutes(store.getters.addRouters); // 动态添加可访问路由表
          next({...to}); // hack方法 确保addRoutes已完成
        });
      } else {
        // 没有动态改变权限的需求可直接next() 删除下方权限判断 ↓
        /* if (hasPermission(store.getters.roles, to.meta.role)) {
           next()//
         } else {
           next({ path: '/401', query: { noGoBack: true }})
         }*/
        next();
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) { // 在免登录白名单，直接进入
      next();
    } else {
      next('/login'); // 否则全部重定向到登录页
      NProgress.done(); // 在hash模式下 改变手动改变hash 重定向回来 不会触发afterEach 暂时hack方案 ps：history模式下无问题，可删除该行！
    }
  }
});

// 结束Progress
router.afterEach(() => NProgress.done());
