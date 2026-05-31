package com.example.userlist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.userlist.ui.theme.UserListTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: UserViewModel = viewModel()
            // アプリ起動時に一度だけ API を叩く
            LaunchedEffect(Unit) {
                viewModel.loadUsers()
            }
            val userList = viewModel.users
            UserListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(userList)
                }
            }
        }
    }
}

@Composable
fun UserListScreen(
    userList: List<User>,
    isCompact: Boolean,
    onUserClick: (String) -> Unit,
    onIconClick: () -> Unit
) {
    if (isCompact) { //展開時
        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .fillMaxHeight()
                .background(Color(0xFFECEFF1)) //リスト背景
                .padding(
                    top = 15.dp,
                    start = 10.dp,
                    end = 10.dp,
                    bottom = 10.dp
                )
        )
        {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(userList) { user ->

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xffB0BBC5))
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable {
                                onUserClick(user.login)
                                onIconClick()
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .width(50.dp)
                        ) {
                            AsyncImage(
                                model = user.avatarUrl,   // アイコン画像
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color.LightGray, shape = CircleShape)
                                    .clip(CircleShape)
                            )
                        }
                        Box(
                        ) {
                            Text(
                                text = user.login,
                                modifier = Modifier.align(Alignment.CenterStart),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.SansSerif

                            )
                        }
                    }

                }
            }
        }
    } else { //アイコンだけ時
        Box(
            modifier = Modifier
                .width(90.dp)
                .fillMaxHeight()
                .background(Color(0xFFECEFF1)) //リスト背景
                .padding(
                    top = 15.dp,
                    start = 10.dp,
                    end = 10.dp,
                    bottom = 10.dp
                )
        )
        {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(userList) { user ->

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xffB0BBC5))
                            .fillMaxWidth()
                            .padding(10.dp)

                            .clickable {
                                onIconClick()
                            }
                    ) {
                        Box(
                        ) {
                            AsyncImage(
                                model = user.avatarUrl,   // アイコン画像
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color.LightGray, shape = CircleShape)
                                    .clip(CircleShape)
                            )
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun UserHeader(onIconClick: () -> Unit) { //一番上のヘッダー　クリック時リストの状態を切り替える
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
            .background(Color(0xFFFFFFFF))
            .drawBehind {
                drawLine(
                    color = Color(0x33000000),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 1.dp.toPx()
                )
            }

    )
    {
        Box( //ヘッダアイコン
            modifier = Modifier
                .padding(start = 40.dp)
                .size(50.dp)
                .background(Color(0xFF546E7A), shape = CircleShape)
                .align(Alignment.CenterStart)
                .clickable {
                    onIconClick()
                }
        )
        {
            Text(
                text = "張",
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }
    }
}

@Composable
fun MainScreen(userList: List<User>) {
    val viewModel: UserViewModel = viewModel()
    val detail = viewModel.userDetail
    var isCompactOfList by remember { mutableStateOf(true) }
    var isCompactOfFollow by remember { mutableStateOf(true) }
    //TRUE: 名前リスト　FALSE: アイコンリスト
    val onIconClick = {
        isCompactOfList = !isCompactOfList
        isCompactOfFollow = true
    }
    //TRUE: 詳細ページ表示　FALSE: フォロー/フォロワーリスト表示
    val onFollowClick = { isCompactOfFollow = !isCompactOfFollow }
    Column(Modifier.fillMaxSize()) {
        UserHeader(
            onIconClick
        )
        Box(Modifier.fillMaxSize()) {
            Row(Modifier.fillMaxSize()) {
                Box() {
                    // 左側　ユーザリスト
                    UserListScreen(
                        userList = userList,
                        isCompact = isCompactOfList,
                        onUserClick = { login ->
                            viewModel.loadUserPage(login)
                        },
                        onIconClick
                    )
                }
                Box(
                ) {
                    //右側　詳細ページ
                    if (detail != null) {
                        DetailScreen(
                            detail,
                            isCompact = isCompactOfList,
                            isCompact2 = isCompactOfFollow,
                            onClickFollowerOrFollow = { login ->
                                viewModel.getFollowerOrFollow(login)
                            },
                            onUserClick = { login ->
                                viewModel.loadUserPage(login)
                            },
                            onIconClick,
                            onFollowClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UserDetailHeader(userDetail: UserDetail) { //詳細ページヘッダー
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.15f)

    )
    {
        Box( //ヘッダアイコン
            modifier = Modifier
                .padding(start = 20.dp)
                .align(Alignment.CenterStart)
        )
        {
            AsyncImage(
                model = userDetail.avatarUrl,   // アイコン画像
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
                    .background(Color.LightGray, shape = CircleShape)
                    .clip(CircleShape)
            )
        }
    }
}

@Composable
fun UserDetailProfile(
    userDetail: UserDetail,
    onClickFollowerOrFollow: (String) -> Unit,
    onFollowClick: () -> Unit,
    onChangeFollowList: (Boolean) -> Unit
) { //詳細ページプロフィール
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f)

    )
    {
        Column() {
            Text(
                text = userDetail.login, //ID
                color = Color.Black
            )
            Text(
                text = userDetail.name ?: "", //名前
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            if (userDetail.email != null) {
                Text(
                    text = "mail: ${userDetail.email}", //メール
                    color = Color.Black
                )
            } else {
                Text(
                    text = "mail: ", //メール
                    color = Color.Black
                )
            }
            if (userDetail.location != null) { //居住地
                Text(
                    text = "Loc. ${userDetail.location}",
                    color = Color.Black
                )
            } else {
                Text(
                    text = "Loc. ",
                    color = Color.Black
                )
            }
            Box(Modifier.fillMaxSize()) {
                Row(
                    Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .clickable {
                                onClickFollowerOrFollow(userDetail.login)
                                onFollowClick()
                                onChangeFollowList(true)
                            }
                    ) {
                        Text(
                            text = "フォロー中： ${userDetail.following}",
                            color = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(
                        modifier = Modifier
                            .clickable {
                                onClickFollowerOrFollow(userDetail.login)
                                onFollowClick()
                                onChangeFollowList(false)
                            }
                    ) {
                        Text(
                            text = "フォロワー： ${userDetail.followers}",
                            color = Color.Black
                        )
                    }
                }
            }
        }

    }
}

//イベントプルダウン
@Composable
fun EventTypeDropdown(
    eventList: List<Event>,
    selectEventsType: (String) -> Unit,
    isFromOld: Boolean,
    ChangeFromOldOrNew: () -> Unit
) {
    //プルダウンの開閉
    var expanded by remember { mutableStateOf(false) }
    // event.type 重複なしリスト
    val typeList = eventList.map { it.type }.distinct()

    Box( //抽出条件ヘッダ
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                //下線
                drawLine(
                    color = Color(0x33000000),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 2.dp.toPx()
                )
            }
            .padding(5.dp)
    ) {
        Row() {
            Box( //イベント種類プルダウン
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color(0xffE0E0E0))
                    .clickable { expanded = true },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "イベント",
                    fontSize = 18.sp,
                )
                // プルダウン
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    // 「すべて」項目
                    DropdownMenuItem(
                        text = { Text("すべて") },
                        onClick = {
                            selectEventsType("すべて")
                            expanded = false
                        }
                    )
                    // event.type の重複なしリストを表示
                    typeList.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type) },
                            onClick = {
                                selectEventsType(type)
                                expanded = false
                            }
                        )
                    }
                }
            }
            Box( //日付昇順降順切り替え
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clickable { ChangeFromOldOrNew() },
                contentAlignment = Alignment.Center
            ) {
                if (isFromOld) {
                    Text(
                        text = "昇順　↓"
                    )
                } else {
                    Text(
                        text = "降順　↑"
                    )
                }
            }

        }
    }
}

//イベント表示エリア
@Composable
fun UserEventsOfDetailScreen(
    eventsList: List<Event>,
    receivedEventsList: List<Event>,
    createRepositoryList: List<ReposDetail>,
    onUserClick: (String) -> Unit
) {
    //タブ初期値
    var clickedTab by remember { mutableStateOf("1") }
    //イベント初期値
    var selectedType by remember { mutableStateOf("すべて") }
    //並び順初期値
    var isFromOld by remember { mutableStateOf(true) }
    val resetValue: () -> Unit = {
        selectedType = "すべて"
        isFromOld = true
    }
    val onClickTab: (String) -> Unit = { tabId ->
        clickedTab = tabId
    }
    Box(
    ) {
        Column(
        ) {
            Box( //イベントタブ
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.06f)
            ) {
                Row(
                ) { //イベント選択タブ
                    Box( //自分のイベント
                        modifier = Modifier
                            .weight(1f)
                            .drawBehind {
                                if (clickedTab != "1") { //選択中の場合、下線を非表示
                                    //下線
                                    drawLine(
                                        color = Color(0x33000000),
                                        start = Offset(0f, size.height),
                                        end = Offset(size.width, size.height),
                                        strokeWidth = 1.dp.toPx()
                                    )
                                }
                                // 右線
                                drawLine(
                                    color = Color(0x33000000),
                                    start = Offset(size.width, 0f),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = 1.dp.toPx()
                                )
                            }
                            .padding(1.dp)
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .clickable {
                                onClickTab("1")
                                resetValue()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "自分",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Box( //受け取ったイベント
                        modifier = Modifier
                            .weight(1f)
                            .drawBehind {
                                if (clickedTab != "2") { //選択中の場合、下線を非表示
                                    //下線
                                    drawLine(
                                        color = Color(0x33000000),
                                        start = Offset(0f, size.height),
                                        end = Offset(size.width, size.height),
                                        strokeWidth = 1.dp.toPx()
                                    )
                                }
                                // 右線
                                drawLine(
                                    color = Color(0x33000000),
                                    start = Offset(size.width, 0f),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = 1.dp.toPx()
                                )
                            }
                            .padding(1.dp)
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .clickable {
                                onClickTab("2")
                                resetValue()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "受信",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Box( //作成したリポジトリ
                        modifier = Modifier
                            .weight(1f)
                            .drawBehind {
                                if (clickedTab != "3") { //選択中の場合、下線を非表示
                                    //下線
                                    drawLine(
                                        color = Color(0x33000000),
                                        start = Offset(0f, size.height),
                                        end = Offset(size.width, size.height),
                                        strokeWidth = 1.dp.toPx()
                                    )
                                }
                            }
                            .padding(1.dp)
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .clickable {
                                onClickTab("3")
                                resetValue()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "作品",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            if (clickedTab != "3") {
                var tempEventsList: List<Event>

                if (clickedTab == "1") { //自分タブ
                    tempEventsList = eventsList
                } else { //受信タブ
                    tempEventsList = receivedEventsList
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.1f)
                ) {
                    EventTypeDropdown(
                        tempEventsList,
                        selectEventsType = { newType ->
                            selectedType = newType
                        },
                        isFromOld,
                        ChangeFromOldOrNew = { isFromOld = !isFromOld }
                    )
                }
                //イベント種類でフィルター
                val filteredList = if (selectedType == "すべて") {
                    //日にちでソート
                    if (isFromOld) {
                        tempEventsList.sortedBy { it.createdAt }
                    } else {
                        tempEventsList.sortedByDescending { it.createdAt }
                    }
                } else {
                    if (isFromOld) {
                        tempEventsList.sortedBy { it.createdAt }
                            .filter { it.type == selectedType }
                    } else {
                        tempEventsList.sortedByDescending { it.createdAt }
                            .filter { it.type == selectedType }
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(10.dp)
                ) { //イベントリスト表示

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(filteredList) { event ->
                            Log.d("EVENT_DETAIL", event.payload.toString())
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .fillMaxWidth()
                                    .drawBehind {
                                        //下線
                                        drawLine(
                                            color = Color(0x33000000),
                                            start = Offset(0f, size.height),
                                            end = Offset(size.width, size.height),
                                            strokeWidth = 2.dp.toPx()
                                        )
                                    }
                                    .padding(10.dp)
                                    .clickable {
                                    }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(50.dp)
                                        .clickable {
                                            onClickTab("1")
                                            resetValue()
                                            onUserClick(event.actor.login)
                                        }
                                ) {
                                    AsyncImage(
                                        model = event.actor.avatarUrl,   // 実行者 - アイコン画像
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(40.dp)
                                            .background(Color.LightGray, shape = CircleShape)
                                            .clip(CircleShape)
                                    )
                                }
                                Box(
                                ) {
                                    Column() {
                                        Text(
                                            text = event.actor.login, //実行者
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.SansSerif

                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Text(
                                            text = event.type, //実行イベントタイプ
                                            fontSize = 24.sp,
                                            fontFamily = FontFamily.SansSerif

                                        )
                                        Spacer(modifier = Modifier.height(5.dp))
                                        Text(
                                            text = event.repo.name, //対象リポジトリ
                                            fontSize = 20.sp,
                                            fontFamily = FontFamily.SansSerif

                                        )
                                        Spacer(modifier = Modifier.height(5.dp))
                                        val context = LocalContext.current

                                        ClickableText( //リポジトリURL
                                            text = AnnotatedString(
                                                text = event.repo.url,
                                                spanStyle = SpanStyle(
                                                    color = Color(0xFF1A73E8),
                                                    textDecoration = TextDecoration.Underline,
                                                    fontSize = 20.sp,
                                                    fontFamily = FontFamily.SansSerif
                                                )
                                            ),
                                            onClick = {
                                                val intent = Intent(
                                                    Intent.ACTION_VIEW,
                                                    Uri.parse(event.repo.url)
                                                )
                                                context.startActivity(intent)
                                            }
                                        )
                                        Spacer(modifier = Modifier.height(5.dp))

                                        val formatter =
                                            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
                                        val dateTime = Instant.parse(event.createdAt)
                                            .atZone(ZoneId.systemDefault())
                                            .toLocalDateTime()
                                        Text(
                                            text = formatter.format(dateTime), //実行日時
                                            fontSize = 20.sp,
                                            fontFamily = FontFamily.SansSerif

                                        )
                                    }
                                }
                            }

                        }
                    }
                }
            } else { //作品タブ
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(createRepositoryList) { repo ->
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .fillMaxWidth()
                                .drawBehind {
                                    //下線
                                    drawLine(
                                        color = Color(0x33000000),
                                        start = Offset(0f, size.height),
                                        end = Offset(size.width, size.height),
                                        strokeWidth = 2.dp.toPx()
                                    )
                                }
                                .padding(10.dp)
                                .clickable {
                                }
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(50.dp)
                                    .clickable {
                                        onClickTab("1")
                                        resetValue()
                                        onUserClick(repo.owner.login)
                                    }
                            ) {
                                AsyncImage(
                                    model = repo.owner.avatarUrl,   // 実行者 - アイコン画像
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(Color.LightGray, shape = CircleShape)
                                        .clip(CircleShape)
                                )
                            }
                            Box(
                            ) {
                                Column() {
                                        Text(
                                            text = repo.owner.login, //実行者
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.SansSerif

                                        )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = repo.name, //リポジトリ名
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.SansSerif

                                    )
                                    Spacer(modifier = Modifier.height(5.dp))
                                    if(repo.description != null) {
                                        Text(
                                            text = repo.description, //リポジトリ説明
                                            fontSize = 20.sp,
                                            fontFamily = FontFamily.SansSerif

                                        )
                                    }
                                    Spacer(modifier = Modifier.height(5.dp))
                                    val context = LocalContext.current

                                    ClickableText( //リポジトリURL
                                        text = AnnotatedString(
                                            text = repo.url,
                                            spanStyle = SpanStyle(
                                                color = Color(0xFF1A73E8),
                                                textDecoration = TextDecoration.Underline,
                                                fontSize = 20.sp,
                                                fontFamily = FontFamily.SansSerif
                                            )
                                        ),
                                        onClick = {
                                            val intent = Intent(
                                                Intent.ACTION_VIEW,
                                                Uri.parse(repo.url)
                                            )
                                            context.startActivity(intent)
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(5.dp))

                                    val formatter =
                                        DateTimeFormatter.ofPattern("yyyy/MM/dd")
                                    val createDateTime = Instant.parse(repo.createdAt)
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDateTime()
                                    val updateDateTime = Instant.parse(repo.createdAt)
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDateTime()
                                    Text(
                                        text = "created at " + formatter.format(createDateTime), //実行日時
                                        fontSize = 20.sp,
                                        fontFamily = FontFamily.SansSerif

                                    )
                                    Text(
                                        text = "updated at " + formatter.format(updateDateTime), //実行日時
                                        fontSize = 20.sp,
                                        fontFamily = FontFamily.SansSerif

                                    )
                                }
                            }
                        }

                    }
                }
            }
        }
    }

}

@Composable
fun UserFollowListScreen(
    userDetail: UserDetail,
    followList: List<User>,
    followerList: List<User>,
    isFollowList: Boolean,
    onChangeFollowList: (Boolean) -> Unit,
    onFollowClick: () -> Unit,
    onUserClick: (String) -> Unit
) {
    Column() {
        Box( //戻ボタン、ユーザ名
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.04f)
                .drawBehind {
                    drawLine(
                        color = Color(0x33000000),
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 2.dp.toPx()
                    )
                }
        ) {
            Row() {
                Box( //戻りボタン
                    modifier = Modifier
                        .fillMaxWidth(0.1f)
                        .padding(start = 5.dp, end = 5.dp)
                        .clickable {
                            onFollowClick()
                        }
                ) {
                    Text(
                        text = "←",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box( //ユーザ名
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = userDetail.login,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Box( //フォロー/フォロワー部ヘッダ
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.06f)
        ) {
            Row(
            ) {
                Box( //フォローボタン
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(
                            if (isFollowList) Color(0xFFE0E0E0) else Color(0xFFF5F5F5)
                        )
                        .clickable {
                            onChangeFollowList(true)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "フォロー",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                }
                Box( //フォロワーボタン
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(
                            if (!isFollowList) Color(0xFFE0E0E0) else Color(0xFFF5F5F5)
                        )
                        .clickable {
                            onChangeFollowList(false)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "フォロワー",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight()
                .background(Color(0xFFE0E0E0)) //リスト背景
                .padding(
                    top = 15.dp,
                    start = 10.dp,
                    end = 10.dp,
                    bottom = 10.dp
                )
        )
        {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(if (isFollowList) followList else followerList) { user ->

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xffBDBDBD))
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable {
                                onUserClick(user.login)
                                onFollowClick()
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .width(50.dp)
                        ) {
                            AsyncImage(
                                model = user.avatarUrl,   // アイコン画像
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color.LightGray, shape = CircleShape)
                                    .clip(CircleShape)
                            )
                        }
                        Box(
                        ) {
                            Text(
                                text = user.login,
                                modifier = Modifier.align(Alignment.CenterStart),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.SansSerif

                            )
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun DetailScreen(
    userDetail: UserDetail,
    isCompact: Boolean,
    isCompact2: Boolean,
    onClickFollowerOrFollow: (String) -> Unit,
    onUserClick: (String) -> Unit,
    onIconClick: () -> Unit,
    onFollowClick: () -> Unit
) {
    val viewModel: UserViewModel = viewModel()
    //フォローリスト
    val followList: List<User> = viewModel.followList
    //フォロワーリスト
    val followerList: List<User> = viewModel.followerList
    //イベントリスト
    val eventsList: List<Event> = viewModel.eventsList
    //参加リポジトリイベントリスト
    val receivedEventsList: List<Event> = viewModel.receivedEventsList
    //作品リスト
    val createRepositoryList: List<ReposDetail> = viewModel.createRepositoryList

    var isFollowList by remember { mutableStateOf(true) }
    if (isCompact) { //名前リスト時
        Box(
        ) {

        }
    } else {
        if (isCompact2) { //詳細ページ表示時
            Column(Modifier.fillMaxSize()) {
                //詳細ページのヘッダ
                UserDetailHeader(userDetail)
                //プロフィール欄
                UserDetailProfile(
                    userDetail,
                    onClickFollowerOrFollow,
                    //TRUE: 詳細ページ表示　FALSE: フォロー/フォロワーリスト表示
                    onFollowClick,
                    onChangeFollowList = { isFollowList = it }
                )
                //イベント欄
                UserEventsOfDetailScreen(
                    eventsList,
                    receivedEventsList,
                    createRepositoryList,
                    onUserClick = { login ->
                        viewModel.loadUserPage(login)
                    })

            }
        } else { //フォロー/フォロワーリスト表示
            UserFollowListScreen(
                userDetail = userDetail,
                followList = followList,
                followerList = followerList,
                isFollowList = isFollowList,
                onChangeFollowList = { isFollowList = it },
                onFollowClick,
                onUserClick = { login -> viewModel.loadUserPage(login) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UserListTheme {
        //UserListScreen()
    }
}