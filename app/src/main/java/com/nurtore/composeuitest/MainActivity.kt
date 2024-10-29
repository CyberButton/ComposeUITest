package com.nurtore.composeuitest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nurtore.composeuitest.ui.theme.ComposeUITestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeUITestTheme {
                MainScreenContents()
            }
        }
    }
}

@Composable
fun MainScreenContents(modifier: Modifier = Modifier) {
    var isInSearchMode by remember { mutableStateOf(false) }

    Scaffold(modifier = modifier, topBar = {
        TopAppBarRow(isInSearchMode = isInSearchMode,
            setSearchMode = { newState -> isInSearchMode = newState })
    }, bottomBar = {
        AnimatedVisibility(visible = !isInSearchMode) {
            BottomAppBarRow()
        }
    }, content = { innerPadding ->
        Box(modifier = modifier.background(color = Color(0xFFF7F7F7))) {
            AnimatedVisibility(
                visible = !isInSearchMode, enter = fadeIn(), exit = fadeOut()
            ) {
                ShapeBackground()
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BalanceSectionMainScreen()
                    TransferOptionsRow()
                    TransactionSheet()
                }
            }
            AnimatedVisibility(
                visible = isInSearchMode,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                BackgroundShapeForSearchMode()
            }
        }
    })
}

@Composable
fun BackgroundShapeForSearchMode(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.LightGray, shape = RectangleShape)
    )
}

@Composable
fun ShapeBackground(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .drawWithCache {
            onDrawBehind {
                drawRect(color = Color(0xFF304FFF))
            }
        }
        .fillMaxWidth()
        .fillMaxHeight(0.44f))
}

@Composable
fun TopAppBarRow(isInSearchMode: Boolean, setSearchMode: (Boolean) -> Unit) {
    val width: Float by animateFloatAsState(if (isInSearchMode) 1f else 0.75f, label = "width")
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        AnimatedVisibility(
            visible = !isInSearchMode,
            enter = slideInHorizontally(initialOffsetX = { -it }),
            exit = slideOutHorizontally(targetOffsetX = { -it })
        ) {
            Icon(
                painter = painterResource(id = R.drawable.trophy_star),
                tint = Color.White,
                contentDescription = "Trophy icon"
            )
        }
        Box(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth(width),
            contentAlignment = Alignment.Center
        ) {
            SearchBarMainScreen(
                isInSearchMode = isInSearchMode, setSearchMode = setSearchMode
            )
        }
        AnimatedVisibility(
            visible = !isInSearchMode,
            enter = slideInHorizontally(initialOffsetX = { it }),
            exit = slideOutHorizontally(targetOffsetX = { it })
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                tint = Color.White,
                contentDescription = "Notification icon",
            )
        }
    }
}

class NavigationItem(
    val id: Int,
    val iconUnselected: Int,
    val iconSelected: Int,
)

val listOfNavigationItems = listOf(
    NavigationItem(
        id = 0, iconUnselected = R.drawable.home, iconSelected = R.drawable.home_filled
    ), NavigationItem(
        id = 1, iconUnselected = R.drawable.chart, iconSelected = R.drawable.chart_filled
    ), NavigationItem(
        id = 2, iconUnselected = R.drawable.scanner, iconSelected = R.drawable.scanner
    ), NavigationItem(
        id = 3, iconUnselected = R.drawable.chat, iconSelected = R.drawable.chat_filled
    ), NavigationItem(
        id = 4, iconUnselected = R.drawable.user, iconSelected = R.drawable.user_filled
    )
)

@Composable
fun BottomAppBarRow(modifier: Modifier = Modifier) {
    var selectedNavItemId by remember { mutableIntStateOf(0) }
    Box(modifier = modifier.padding(12.dp)) {
        Card(
            colors = CardDefaults.cardColors().copy(containerColor = Color.White)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(62.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOfNavigationItems.forEach { navItem ->
                    NavItemBlock(navItem = navItem,
                        isSelected = navItem.id == selectedNavItemId,
                        onSelect = { selectedId -> selectedNavItemId = selectedId })
                }
            }
        }
    }
}

@Composable
fun NavItemBlock(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    navItem: NavigationItem,
    onSelect: (Int) -> Unit
) {

    AnimatedContent(targetState = isSelected, label = "NavItem Animation") { targetState ->
        if (targetState) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = navItem.iconSelected),
                    tint = Color(0xFF304FFF),
                    contentDescription = ""
                )
                Box(
                    modifier = modifier
                        .size(6.dp)
                        .background(
                            color = Color(0xFF304FFF), shape = CircleShape
                        )
                )
            }
        } else {
            Icon(painter = painterResource(id = navItem.iconUnselected),
                contentDescription = "",
                modifier = modifier.clickable { onSelect(navItem.id) })
        }
    }

}

@Composable
fun SearchBarMainScreen(
    modifier: Modifier = Modifier, isInSearchMode: Boolean, setSearchMode: (Boolean) -> Unit
) {
    var text by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    TextField(modifier = modifier
        .onFocusChanged {
            setSearchMode(it.isFocused)
        }
        .then(
            if (isInSearchMode) {
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            } else {
                Modifier
            }
        ),
        value = text,
        onValueChange = { text = it },
        label = { Text(text = "Search \"Payments\"") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = Color.White,
                contentDescription = "Search icon"
            )
        },
        trailingIcon = {
            AnimatedVisibility(visible = isInSearchMode) {
                Icon(
                    tint = Color.White, modifier = modifier.clickable {
                        focusManager.clearFocus()
                    }, imageVector = Icons.Default.Close, contentDescription = "Close icon"
                )
            }
        },
        shape = RoundedCornerShape(26.dp),
        colors = TextFieldDefaults.colors().copy(
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White,
            focusedContainerColor = Color(0xFF778BFE),
            unfocusedContainerColor = Color(0xFF778BFE)
        )
    )
}

@Composable
fun BalanceSectionMainScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(52.dp))
        CurrencySelector()
        FormattedBalance()
        BalanceInfo()
        AddMoneyButton()
    }
}

@Composable
fun CurrencySelector(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.us),
            modifier = modifier.height(14.dp),
            contentDescription = "US flag"
        )
        Text(
            text = "US Dollar", fontSize = 14.sp, color = Color.White
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            tint = Color.White,
            modifier = modifier.height(14.dp),
            contentDescription = "Arrow down"
        )
    }
}

@Composable
fun FormattedBalance(modifier: Modifier = Modifier) {
    Text(
        text = "$20,000", fontSize = 46.sp, color = Color.White
    )
}

@Composable
fun BalanceInfo(modifier: Modifier = Modifier) {
    Text(
        text = "Available Balance", fontSize = 12.sp, color = Color.White
    )
}

@Composable
fun AddMoneyButton(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Button(
        onClick = { Toast.makeText(context, "Add Money", Toast.LENGTH_SHORT).show() },
        colors = ButtonDefaults.buttonColors().copy(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, Color.White)

    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.wallet), contentDescription = "Add icon"
            )
            Text(text = "Add Money")
        }
    }
}

@Composable
fun TransferOptionsRow(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .height(80.dp),
        colors = CardDefaults.cardColors().copy(containerColor = Color.White)
    ) {
        Row(
            modifier = modifier
                .padding(top = 16.dp, bottom = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TransferOptionSend()
            VerticalDivider(modifier = modifier.height(30.dp))
            TransferOptionRequest()
            VerticalDivider(modifier = modifier.height(30.dp))
            TransferOptionBank()
        }
    }
}

@Composable
fun TransferOptionSend(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.dollar_send_circle),
            tint = Color.Blue,
            contentDescription = "Send icon"
        )
        Text(text = "Send")
    }
}

@Composable
fun TransferOptionRequest(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.dollar_receive_circle),
            tint = Color(0xFFF9A825),
            contentDescription = "Request icon"
        )
        Text(text = "Request")
    }
}

@Composable
fun TransferOptionBank(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.bank),
            tint = Color(0xFFF9A825),
            contentDescription = "Bank icon"
        )
        Text(text = "Bank")
    }
}

@Composable
fun TransactionSheet(modifier: Modifier = Modifier) {
    Column {
        TransactionSheetTop()
        TransactionsList()
    }
}

@Composable
fun TransactionSheetTop(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = modifier.padding(start = 12.dp), text = "Transaction"
        )
        Icon(
            modifier = modifier.padding(end = 12.dp),
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Arrow forward"
        )
    }
}

class Transaction(
    val iconId: Int,
    val iconColor: Color,
    val iconBackgroundColor: Color,
    val text: String,
    val formattedSum: String,
    val sumColor: Color
)

@Composable
fun TransactionsList(modifier: Modifier = Modifier) {

    val transactions = listOf(
        Transaction(
            iconId = R.drawable.credit_card_minus,
            text = "Spending",
            formattedSum = "-$500",
            sumColor = Color.Red,
            iconColor = Color.Blue,
            iconBackgroundColor = Color(0xFFEAEBFF)
        ), Transaction(
            iconId = R.drawable.coins,
            text = "Income",
            formattedSum = "$3000",
            sumColor = Color(0xFF439F48),
            iconColor = Color(0xFF439F48),
            iconBackgroundColor = Color(0xFFEAF7EC)
        ), Transaction(
            iconId = R.drawable.invoice,
            text = "Bills",
            formattedSum = "-$800",
            sumColor = Color.Red,
            iconColor = Color(0xFFFB8F0B),
            iconBackgroundColor = Color(0xFFFFF9C5)
        ), Transaction(
            iconId = R.drawable.sack_dollar,
            text = "Savings",
            formattedSum = "-$500",
            sumColor = Color(0xFFFB8F0B),
            iconColor = Color(0xFFFB8F0B),
            iconBackgroundColor = Color(0xFFFEF7E8)
        )
    )

    Box(modifier = modifier.padding(12.dp)) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = Color.White, shape = RoundedCornerShape(12.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            transactions.forEach { transaction ->
                TransactionRow(
                    transactionTypeIcon = transaction.iconId,
                    transactionTypeText = transaction.text,
                    formattedSum = transaction.formattedSum,
                    sumColor = transaction.sumColor,
                    iconColor = transaction.iconColor,
                    iconBackgroundColor = transaction.iconBackgroundColor
                )
                if (transaction.iconId != R.drawable.sack_dollar) {
                    HorizontalDivider(modifier = modifier.width(340.dp))
                }
            }
        }
    }
}

@Composable
fun TransactionRow(
    modifier: Modifier = Modifier,
    transactionTypeIcon: Int,
    transactionTypeText: String,
    formattedSum: String,
    sumColor: Color,
    iconColor: Color,
    iconBackgroundColor: Color
) {
    Row(
        modifier = modifier
            .padding(12.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TransactionTypeBlock(
            iconId = transactionTypeIcon,
            iconColor = iconColor,
            iconBackgroundColor = iconBackgroundColor,
            text = transactionTypeText
        )
        TransactionFormattedAmountBlock(formattedSum = formattedSum, color = sumColor)
    }
}

@Composable
fun TransactionTypeBlock(
    modifier: Modifier = Modifier,
    iconId: Int,
    iconColor: Color,
    iconBackgroundColor: Color,
    text: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconInShape(
            iconId = iconId, iconColor = iconColor, iconBackgroundColor = iconBackgroundColor
        )
        Text(text = text, fontSize = 14.sp)
    }
}

@Composable
fun IconInShape(
    modifier: Modifier = Modifier, iconId: Int, iconColor: Color, iconBackgroundColor: Color
) {
    Box(
        modifier = modifier
            .background(
                color = iconBackgroundColor, shape = CircleShape
            )
            .padding(8.dp), contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconId), tint = iconColor, contentDescription = null
        )
    }
}

@Composable
fun TransactionFormattedAmountBlock(
    modifier: Modifier = Modifier, formattedSum: String, color: Color
) {
    Row {
        Text(
            text = formattedSum, color = color
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Arrow right"
        )
    }
}

@Preview(
    showBackground = true, showSystemUi = true
)
@Composable
fun GreetingPreview() {
    ComposeUITestTheme {
        MainScreenContents()
    }
}