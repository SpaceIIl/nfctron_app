package com.example.nfctron_app.nasaDaily.jetpackCompose

//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.ExperimentalComposeUiApi
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.semantics.Role.Companion.Image
//import androidx.compose.ui.unit.dp
//import androidx.constraintlayout.widget.ConstraintLayout
//import com.example.nfctron_app.R
//import kotlinx.coroutines.NonDisposableHandle.parent
//
//@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//fun itemLaunch() {
//    ConstraintLayout(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 16.dp)
//    ) {
//        val (cardImage, textLaunchTitle, textCountdown, cardIconImageView, cardLivestream, iconShare, textWiki) = createRefs(Modifier)
//
//        Card(
//            modifier = Modifier
//                .constrainAs(cardImage, {
//                    start.linkTo(parent.start)
//                    top.linkTo(parent.top)
//                })
//                .size(50.dp),
//            shape = RoundedCornerShape(0.dp),
//            elevation = 0.dp
//        ) {
//            Image(
//                painter = painterResource(R.drawable.your_image_resource),
//                contentDescription = null,
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.Crop
//            )
//        }
//
//        Text(
//            text = "Starlink v1.5",
//            modifier = Modifier
//                .constrainAs(textLaunchTitle) {
//                    start.linkTo(cardImage.end, margin = 16.dp)
//                    top.linkTo(cardImage.top)
//                }
//        )
//
//        Text(
//            text = "Launch in 20d 5h 20m 9s",
//            modifier = Modifier
//                .constrainAs(textCountdown) {
//                    start.linkTo(cardImage.end, margin = 16.dp)
//                    bottom.linkTo(cardImage.bottom)
//                }
//        )
//
//        Card(
//            modifier = Modifier
//                .constrainAs(cardIconImageView) {
//                    top.linkTo(parent.top)
//                    bottom.linkTo(parent.bottom)
//                    end.linkTo(parent.end)
//                }
//                .size(60.dp, 70.dp),
//            shape = RoundedCornerShape(10.dp),
//            elevation = 0.dp,
//            backgroundColor = Color.Gray.copy(alpha = 0.5f)
//        ) {
//            Image(
//                painter = painterResource(R.drawable.baseline_push_pin_24),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(24.dp)
//                    .align(Alignment.Center)
//            )
//        }
//
//        Card(
//            modifier = Modifier
//                .constrainAs(cardLivestream) {
//                    start.linkTo(parent.start)
//                    top.linkTo(cardImage.bottom, margin = 16.dp)
//                },
//            shape = RoundedCornerShape(8.dp),
//            elevation = 0.dp,
//            backgroundColor = Color.Red
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.padding(horizontal = 4.dp)
//            ) {
//                Image(
//                    painter = painterResource(R.drawable.baseline_play_arrow_24),
//                    contentDescription = null,
//                    modifier = Modifier.size(20.dp)
//                )
//                Text(
//                    text = "Livestream",
//                    color = Color.White,
//                    modifier = Modifier.padding(start = 4.dp, end = 8.dp)
//                )
//            }
//        }
//
//        Image(
//            painter = painterResource(R.drawable.baseline_link_24),
//            contentDescription = null,
//            modifier = Modifier
//                .constrainAs(iconShare) {
//                    start.linkTo(cardLivestream.end, margin = 24.dp)
//                    top.linkTo(cardLivestream.top)
//                    bottom.linkTo(cardLivestream.bottom)
//                .size(30.dp)
//                }
//        )
//
//        Text(
//            text = "Wiki",
//            modifier = Modifier
//                .constrainAs(textWiki) {
//                    start.linkTo(iconShare.end, margin = 4.dp)
//                    top.linkTo(iconShare.top)
//                    bottom.linkTo(iconShare.bottom)
//                }
//        )
//    }
//}
