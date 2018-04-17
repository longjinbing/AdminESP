<template>
  <div class='tabs-view-container'>
     <router-link class="tabs-view" v-for="tag in Array.from(visitedViews)" :to="tag.path" :key="tag.path" v-if="tag.path != '/admin/uLayout'">
      <el-tag :closable="true" :type="isActive(tag.path)?'primary':''" @close='closeViewTabs(tag,$event)'>
        {{tag.name}}
      </el-tag>
    </router-link>
  </div>
</template>

<script>
export default {
  computed: {
    visitedViews() {
      return this.$store.state.app.visitedViews.slice(-6)
    }
  },
  methods: {
    closeViewTabs(view, $event) {
      this.$store.dispatch('delVisitedViews', view).then((views) => {
        if (this.isActive(view.path)) {
          const latestView = views.slice(-1)[0]
          if (latestView) {
            this.$router.push(latestView.path)
          } else {
            this.$router.push('/')
          }
        }
      })
      $event.preventDefault()
    },
    generateRoute() {
      if (this.$route.matched[this.$route.matched.length - 1].name) {
        return this.$route.matched[this.$route.matched.length - 1]
      }
      this.$route.matched[0].path = '/'
      return this.$route.matched[0]
    },
    addViewTabs() {
      this.$store.dispatch('addVisitedViews', this.generateRoute())
    },
    isActive(path) {
      return path === this.$route.path
    }
  },
  watch: {
    $route() {
      this.addViewTabs()
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
  .tabs-view-container {
    display: inline-block;
    vertical-align: top;
    margin-left: 10px;
    .tabs-view {
      margin-left: 10px;
      .el-tag {
        /*  background-color: rgba(182, 205, 228, 0.1);
          padding: 0 10px;
          height: 32px;
          line-height: 30px;
          font-size: 12px;
          color: #409EFF;
          border-radius: 4px;
          box-sizing: border-box;
          border: 1px solid rgba(64, 158, 255, .2);*/
        background-color: #8391a5;
        padding: 0 5px;
        height: 32px;
        line-height: 30px;
        font-size: 12px;
        color: #fff;
        border-radius: 4px;
        box-sizing: border-box;
        border: 1px solid transparent;
      }

      .el-tag .el-icon-close {
        color:#fff;
      }

      .el-tag .el-icon-close:hover{
        background-color: #fff;
        color: #8391a5;
      }

      .el-tag--primary{
        background-color:rgba(182, 205, 228, 0.1);
        border:  1px solid rgba(64, 158, 255, .2);
        color: #20a0ff;
      }

      .el-tag--primary .el-icon-close{
        color: #20a0ff;
      }

      .el-tag--primary .el-icon-close:hover{
        background-color: #20a0ff;
        color: #fff;
      }
    }
  }





</style>

