package com.example.rrgbs.ui.screens.store

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rrgbs.data.DataSources
import com.example.rrgbs.model.CartItem
import com.example.rrgbs.model.StoreProduct
import com.example.rrgbs.ui.theme.CrimsonRed
import com.example.rrgbs.ui.theme.DeepNavy

@Composable
fun StoreScreen(
    cart: List<CartItem>,
    onUpdateCartQty: (StoreProduct, Int) -> Unit,
    onClearCart: () -> Unit,
    onShowToast: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var isCartOpen by remember { mutableStateOf(false) }
    var isQuoteOpen by remember { mutableStateOf(false) }

    val filteredProducts = remember(searchQuery, selectedCategory) {
        DataSources.STORE_PRODUCTS.filter { product ->
            val matchesQuery = searchQuery.isBlank() ||
                    product.name.contains(searchQuery, ignoreCase = true) ||
                    product.description.contains(searchQuery, ignoreCase = true)

            val matchesCat = selectedCategory == null ||
                    product.category.equals(selectedCategory, ignoreCase = true)

            matchesQuery && matchesCat
        }
    }

    val totalCartCount = cart.sumOf { it.qty }
    val totalCartAmount = cart.sumOf { it.product.price * it.qty }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8FAFC))
        ) {
            // Search and Category Filter Header
            Surface(
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search products, uniforms, IT, gifts...", fontSize = 12.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(18.dp)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        item {
                            FilterChip(
                                selected = selectedCategory == null,
                                onClick = { selectedCategory = null },
                                label = { Text("All Products", fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = DeepNavy,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }

                        items(DataSources.STORE_CATEGORIES) { cat ->
                            FilterChip(
                                selected = selectedCategory == cat.id,
                                onClick = {
                                    selectedCategory = if (selectedCategory == cat.id) null else cat.id
                                },
                                label = { Text("${cat.icon} ${cat.name}", fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = CrimsonRed,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }
            }

            // Products Catalog List
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 90.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Employee Joining Kit Hero Banner
                if (selectedCategory == null || selectedCategory == "Joining Kits") {
                    item {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(DeepNavy, Color(0xFF1E293B))
                                        )
                                    )
                                    .padding(18.dp)
                            ) {
                                Column {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(CrimsonRed)
                                                .padding(horizontal = 8.dp, vertical = 3.dp)
                                        ) {
                                            Text("WELCOME ONBOARDING", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 10.sp)
                                        }

                                        Text("From ₹799 / kit", color = Color(0xFFF59E0B), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Text(
                                        text = "Custom Employee Joining Kits",
                                        color = Color.White,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Text(
                                        text = "High-quality corporate welcome swag: ID cards, lanyards, diary, executive pens, branded mugs & backpacks with your company logo.",
                                        color = Color(0xFFCBD5E1),
                                        fontSize = 12.sp,
                                        lineHeight = 16.sp
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    // Kit Items preview
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                            DataSources.SAMPLE_JOINING_KIT_ITEMS.take(6).chunked(3).forEach { rowItems ->
                                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                                    rowItems.forEach { itemText ->
                                                        Box(
                                                            modifier = Modifier
                                                                .clip(RoundedCornerShape(4.dp))
                                                                .background(Color.White.copy(alpha = 0.15f))
                                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                                        ) {
                                                            Text(itemText, color = Color.White, fontSize = 10.sp)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(14.dp))

                                    Button(
                                        onClick = {
                                            val kitProduct = DataSources.STORE_PRODUCTS.firstOrNull { it.id == 16 }
                                            if (kitProduct != null) {
                                                val existingQty = cart.firstOrNull { it.product.id == 16 }?.qty ?: 0
                                                onUpdateCartQty(kitProduct, existingQty + 1)
                                                onShowToast("Employee Joining Kit added to cart!")
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(imageVector = Icons.Default.AddShoppingCart, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Add Custom Joining Kit to Order", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                }

                // Section Title
                item {
                    Text(
                        text = "${filteredProducts.size} Products & Supplies Available",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )
                }

                // Product items
                items(filteredProducts, key = { it.id }) { product ->
                    val cartItem = cart.firstOrNull { it.product.id == product.id }
                    val currentQty = cartItem?.qty ?: 0

                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Icon Box
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFFF8FAFC)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = product.icon, fontSize = 28.sp)
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            // Details
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(CrimsonRed.copy(alpha = 0.1f))
                                            .padding(horizontal = 5.dp, vertical = 1.dp)
                                    ) {
                                        Text(product.tag, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = CrimsonRed)
                                    }

                                    Text(
                                        text = product.category,
                                        fontSize = 10.sp,
                                        color = Color(0xFF64748B)
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = product.name,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepNavy
                                )

                                Text(
                                    text = product.description,
                                    fontSize = 11.sp,
                                    color = Color(0xFF64748B),
                                    maxLines = 1
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = "₹${product.price}",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = DeepNavy
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            // Add to cart or Qty controls
                            if (currentQty == 0) {
                                Button(
                                    onClick = {
                                        onUpdateCartQty(product, 1)
                                        onShowToast("Added ${product.name} to cart")
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                    modifier = Modifier.height(34.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text("Add", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            } else {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    IconButton(
                                        onClick = { onUpdateCartQty(product, currentQty - 1) },
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFFF1F5F9))
                                    ) {
                                        Icon(imageVector = Icons.Default.Remove, contentDescription = null, modifier = Modifier.size(14.dp))
                                    }

                                    Text(
                                        text = "$currentQty",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = DeepNavy
                                    )

                                    IconButton(
                                        onClick = { onUpdateCartQty(product, currentQty + 1) },
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(CrimsonRed)
                                    ) {
                                        Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Floating Cart Summary Bar
        if (totalCartCount > 0) {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DeepNavy),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clickable { isCartOpen = true }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(CrimsonRed),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$totalCartCount",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = "View Quotation Cart", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text(text = "Estimated: ₹$totalCartAmount", color = Color(0xFFCBD5E1), fontSize = 11.sp)
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Checkout", color = Color(0xFFF59E0B), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFFF59E0B))
                    }
                }
            }
        }
    }

    if (isCartOpen) {
        CartBottomSheet(
            cart = cart,
            onUpdateQty = onUpdateCartQty,
            onClearCart = onClearCart,
            onCheckout = {
                isCartOpen = false
                isQuoteOpen = true
            },
            onDismiss = { isCartOpen = false }
        )
    }

    if (isQuoteOpen) {
        RequestQuoteDialog(
            cart = cart,
            onDismiss = { isQuoteOpen = false },
            onSubmitQuote = { company, person, phone, email, address ->
                onClearCart()
                onShowToast("Quotation request submitted for $company! Our team will contact you at $phone.")
            }
        )
    }
}
