
# Touch模拟实例

[链接](http://mio4kon.com/2015/09/22/Touch%E4%BA%8B%E4%BB%B6%E6%A8%A1%E6%8B%9F/)

关于Touch事件的几个模拟示例.如果对分发机制不是很了解的,又没有太多时间模拟实验的,可以参考.

## 原流程

例子有3个控件:

![](http://7q5ccl.com1.z0.glb.clouddn.com/miotouch事件.jpg)

`GrandparentView` extends `ViewGroup` (粉色)
`ParentView` extends `ViewGroup`      (蓝色)
`ChildView` extends `View`(棕色)

其中GrandparentView是ParentView的父布局,ParentView是ChildView的父布局.

<!--more-->

**模拟1**:仅仅点击`GrandparentView`区域

1. GrandparentView dispatchTouchEvent  ACTION_DOWN
2. GrandparentView onInterceptTouchEvent  ACTION_DOWN
3. GrandparentView onTouchEvent ACTION_DOWN

**模拟2**:点击`ParentView`部分区域

1. GrandparentView dispatchTouchEvent  ACTION_DOWN
2. GrandparentView onInterceptTouchEvent  ACTION_DOWN
3. ParentView dispatchTouchEvent  ACTION_DOWN
4. ParentView onInterceptTouchEvent  ACTION_DOWN
5. ParentView onTouchEvent  ACTION_DOWN
6. GrandparentView onTouchEvent ACTION_DOWN

**模拟3**:点击`ChildView`部分区域	

1. GrandparentView dispatchTouchEvent  ACTION_DOWN
2. GrandparentView onInterceptTouchEvent  ACTION_DOWN
3. ParentView dispatchTouchEvent  ACTION_DOWN
4. ParentView onInterceptTouchEvent  ACTION_DOWN
5. ChildView dispatchTouchEvent  ACTION_DOWN
6. ChildView onTouchEvent  ACTION_DOWN
7. ParentView onTouchEvent  ACTION_DOWN
8. GrandparentView onTouchEvent ACTION_DOWN

以上都是在原基础上打的Log,即不修改任何返回值的情况下的输出顺序.

可以发现:
父`ViewGroup`执行顺序从`dispatchTouchEvent` 到 `onInterceptTouchEvent`,然后询问子`ViewGroup`的`dispatchTouchEvent` 到 `onInterceptTouchEvent`,再然后从子`View`的
`dispatchTouchEvent`到`onTouchEvent`,由于子`View`没有`onInterceptTouchEvent`.所以如果子View不消费事件,就反向到父布局的`onTouchEvent`.

**问题:明明是点击事件,为什么没有消费UP事件?**
实际上,事件传递在View没有消费的时候,最终会传递给Activity的`onTouchEvent`.
所以实际上是如果按照模拟1(其他同理)来说应该是:

1. GrandparentView dispatchTouchEvent  ACTION_DOWN
2. GrandparentView onInterceptTouchEvent  ACTION_DOWN
3. GrandparentView onTouchEvent ACTION_DOWN
4. Activity onTouchEvent ACTION_DOWN
5. Activity onTouchEvent ACTION_UP


## onTouchEvent 相关

用于处理事件.可以消费事件.

**模拟4**:`ChildView`的`onTouchEvent`返回true,点击`ChildView`部分区域

此时应该是分发到最终View--`ChildView`,在反向询问是否`onTouchEvent`消费的时候直接由`ChildView`消费.即:

> 注:点击事件是分发了down 和 up 两个事件.

1. GrandparentView dispatchTouchEvent  ACTION_DOWN
2. GrandparentView onInterceptTouchEvent  ACTION_DOWN
3. ParentView dispatchTouchEvent  ACTION_DOWN
4. ParentView onInterceptTouchEvent  ACTION_DOWN
5. ChildView dispatchTouchEvent  ACTION_DOWN
6. ChildView onTouchEvent  ACTION_DOWN
7. GrandparentView dispatchTouchEvent  ACTION_UP
8. GrandparentView onInterceptTouchEvent  ACTION_UP
9. ParentView dispatchTouchEvent  ACTION_UP
10. ParentView onInterceptTouchEvent  ACTION_UP
11. ChildView dispatchTouchEvent  ACTION_UP
12. ChildView onTouchEvent  ACTION_UP

**模拟5**:`GrandparentView`的`onTouchEvent`返回true,点击`ChildView`部分区域

这个实验几乎与**模拟4**一样.猜想是:
从父类分发,直到ChildView,然后反向轮训到`GrandparentView`的`onTouchEvent`方法消费.
事实的确如此,但有一个有趣的现象:
ACTION_DOWN 的确如此,ACTION_UP却在`GrandparentView`分发后立刻执行了`onTouchEvent`
下面是完整的流程.

1. GrandparentView dispatchTouchEvent  ACTION_DOWN
2. GrandparentView onInterceptTouchEvent  ACTION_DOWN
3. ParentView dispatchTouchEvent  ACTION_DOWN
4. ParentView onInterceptTouchEvent  ACTION_DOWN
5. ChildView dispatchTouchEvent  ACTION_DOWN
6. ChildView onTouchEvent  ACTION_DOWN
7. ParentView onTouchEvent  ACTION_DOWN
8. GrandparentView onTouchEvent ACTION_DOWN
9. GrandparentView dispatchTouchEvent  ACTION_UP   <---**到这里还是挺正常的**
10. GrandparentView onTouchEvent ACTION_UP  

模拟4 是在最底层的View消费事件的.两个事件都会从头传递一遍. 
模拟5 不在最底层消费事件,第一次会从头传递到底层然后反向轮训直到消费.第二次从上层传递,到当前直接进入到onTouch消费.而不会再传到底层.

**当某一层的View消费了事件,那么以后的事件都不会再向下传递**(以后的事件,即没有离开区域.比如说一次点击事件.比如说点击移动事件等)

## dispatchTouchEvent

用于分发事件.也可以消费事件.

**模拟6**:`ParentView`的`dispatchTouchEvent`返回为true,点击`ChildView`部分

1. GrandparentView dispatchTouchEvent  ACTION_DOWN
2. GrandparentView onInterceptTouchEvent  ACTION_DOWN
3. ParentView dispatchTouchEvent  ACTION_DOWN

`ACTION_UP`同理.可见`dispatchTouchEvent`返回为true则直接在这一层消费掉了.

**模拟7**:`GrandparentView`的`dispatchTouchEvent`返回为false,点击`ChildView`部分

1. GrandparentView dispatchTouchEvent  ACTION_DOWN
2. Activity onTouchEvent ACTION_DOWN
3. Activity onTouchEvent ACTION_UP

`dispatchTouchEvent`返回为false,则并不消费,但也不分发.而是反向询问.

## onInterceptTouchEvent

用于拦截事件

**模拟8**:`ParentView`的`onInterceptTouchEvent`返回为true,点击`ChildView`部分

1. GrandparentView dispatchTouchEvent  ACTION_DOWN
2. GrandparentView onInterceptTouchEvent  ACTION_DOWN
3. ParentView dispatchTouchEvent  ACTION_DOWN
4. ParentView onInterceptTouchEvent  ACTION_DOWN <--**到这里中断了,直接反向询问**
5. ParentView onTouchEvent  ACTION_DOWN
6. GrandparentView onTouchEvent ACTION_DOWN
7. Activity onTouchEvent ACTION_DOWN
8. Activity onTouchEvent ACTION_UP


 由于在`ParentView`中中断事件分发.于是在传到`ParentView`中直接调用了`ParentView`的`onTouchEvent`,然而`ParentView`并没有消费此事件于是反向询问直到最后.
 




