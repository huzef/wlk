// pages/addOrUpdateCard/components/resume/resume.js
var app = getApp();
const { $Toast } = require('../../../../dist/base/index');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    showModal: false,
    field: [],
    imges:[],
    items: [
      { name: '保险', value: '保险' },
      { name: '税务', value: '税务'},
      { name: '争议解决', value: '争议解决' },
      { name: '刑事辩护', value: '刑事辩护' },
      { name: '婚姻家庭', value: '婚姻家庭' },
      { name: '继承遗产', value: '继承遗产' },
      { name: '劳动纠纷', value: '劳动纠纷' },
      { name: '交通事故', value: '交通事故' },
      { name: '公司收购', value: '公司收购' },
      { name: '海商海事', value: '海商海事' },
      { name: '知识产权', value: '知识产权' },
      { name: '兼并与重组', value: '兼并与重组' },
      { name: '银行与金融', value: '银行与金融' },
      { name: '证券与资本市场', value: '证券与资本市场' },
      { name: '外商投资与并购', value: '外商投资与并购' },
      { name: '房地产与建设工程', value: '房地产与建设工程' },
      { name: '私募股权与投资基金', value: '私募股权与投资基金' }
  ],
  spinShow:false

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.init();
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  chooseImage() {
    if (this.data.imges.length>=3){
      $Toast({
        content: "只能上传三张",
        type: 'error'
      });
      return false
    }
    const that = this
    wx.chooseImage({
      sourceType: ['album', 'camera'],
      sizeType: ['original', 'compressed'],
      count: 3,
      success(res) {
        console.log(res.tempFilePaths)
      that.setData({
        spinShow:true
      })
        const tempFilePaths = res.tempFilePaths
        wx.uploadFile({
          url: app.globalData.baseService +'file/upload', //
          header: {
            "Content-Type": "application/x-www-form-urlencoded", 'Cookie': wx.getStorageSync("cookie")
          },
          filePath: tempFilePaths[0],
          name: 'file',
          success(res) {
            console.log("最终结果")

            that.setData({
              spinShow: false
            })
            wx.showToast({
              title: '上传成功',
              icon: 'success',
              duration: 1000
            })
            const data = JSON.parse(res.data)
            that.data.imges.push(data.data)
            //do something
            that.setData({
              imageList: that.data.imges
            })

          }
        })
      }
    })
  },
  previewImage(e) {
    const current = e.target.dataset.src

    wx.previewImage({
      current,
      urls: this.data.imageList
    })
  },
  /**
 * 弹窗
 */
  showDialogBtn: function () {
    this.setData({
      showModal: true
    })
  },
  /**
    * 隐藏模态对话框
    */
  hideModal: function () {
    this.setData({
      showModal: false
    });
  },
  /**
   * 对话框取消按钮点击事件
   */
  onCancel: function () {
    this.hideModal();
  },
  /**
   * 对话框确认按钮点击事件
   */
  onConfirm: function () {
    this.hideModal();
  },
  checkboxChange: function (e) {
    console.log('checkbox发生change事件，携带value值为：', e.detail.value)
    this.setData({
      field: e.detail.value
    });
  },
  alert:function(msg){
    $Toast({
      content: msg,
      type: 'error'
    });
  },
  formSubmit: function (e) {
    console.log('form发生了submit事件，携带数据为：', e.detail.value)
    const data = e.detail.value
    data.field=this.data.field;
    data.imges =this.data.imges;
    if (data.imges.length==0) {
      this.alert("请选择形象照！")
      return false;
    }
    if (data.field == null || data.field == "" || data.field==undefined){
      this.alert("请选择擅长领域！")
      return false;
    }
    if (e.detail.value.introduce == null || e.detail.value.introduce == "" || e.detail.value.introduce == undefined) {
      this.alert("请输入您的介绍！")
      return false;
    }
    var that = this;
    wx.request({
      url: app.globalData.baseService + "user/update",
      header: {
        "Content-Type": "application/x-www-form-urlencoded", 'Cookie': wx.getStorageSync("cookie")
      },
      method: "POST",
      data: data,
      //data: { code: code},
      complete: function (res) {
        if(res.data.code==200){
          app.globalData.userInfo = res.data.data
          wx.navigateTo({
            url: '/pages/addOrUpdateCard/components/case/case'
          })
        }else{
          app.loginCode();
        }
        
      }
    })

  },
  init: function () {
    const data = {};
    data.imageId=1;
    var that = this;
    wx.request({
      url: app.globalData.baseService + "user/getOneOfUpdate",
      header: {
        "Content-Type": "application/x-www-form-urlencoded", 'Cookie': wx.getStorageSync("cookie")
      },
      method: "POST",
      data: data,
      //data: { code: code},
      complete: function (res) {
        console.log(res)
        if(res.data.code=200){
          let fd=[]
          if (res.data.data.field != "" && res.data.data.field!=null){
            fd =res.data.data.field.split(",")
          }
          that.data.imges = (res.data.data.wimages.map(function (data) {
            return data.path
          }))
          that.setData({
            user: res.data.data,
            imageList: that.data.imges,
            field: fd
          })

        }else{
          app.loginCode()
        }


      }
    })
  },
  delImage:function(e){
    this.data.imges.splice(e.currentTarget.dataset.index,1)
    this.setData({
      imageList: this.data.imges
    })
  }
})